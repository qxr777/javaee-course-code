package edu.whut.cs.javaee.spring.springbucks.mcp;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.whut.cs.javaee.spring.springbucks.mcp.model.CoffeeOrder;
import edu.whut.cs.javaee.spring.springbucks.mcp.model.Coffee;
import edu.whut.cs.javaee.spring.springbucks.mcp.model.NewOrderRequest;
import edu.whut.cs.javaee.spring.springbucks.mcp.model.OrderState;
import edu.whut.cs.javaee.spring.springbucks.mcp.model.OrderStateRequest;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author QiXin
 * @date 2025/4/7
 */
@Service
public class CoffeeService {
    private static final String BASE_URL = "http://localhost";

    private final RestClient restClient;

    public CoffeeService() {

        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("User-Agent", "CoffeeApiClient/1.0 (your@email.com)")
                .build();
    }

    public record BookingDetailsRequest(String bookingNumber, String name) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record BookingDetails(String bookingNumber, String name, String items, Date date, OrderState orderState,
                                 Integer discount, Long total, String waiter, String barista) {
    }

    public record BookingCoffeeRequest(String customer, List<String> items) {
    }

    @Tool(description = "获取订单详细信息")
    public CoffeeService.BookingDetails getOrderDetails(String bookingNumber, String customerName) {
//        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: 得到订单详情 %s for %s...%n", bookingNumber, customerName);
//        System.out.println("==========================================================================================");
//        CoffeeOrder coffeeOrder = coffeeOrderService.getOrder(Long.parseLong(bookingNumber));

        CoffeeOrder coffeeOrder = restClient.get().uri("/waiter-service/order/{id}", bookingNumber).retrieve().body(CoffeeOrder.class);

        if (coffeeOrder != null) {
            // 从Coffee对象中提取名称并拼接为字符串
            String items = coffeeOrder.getItems().stream()
                    .map(Coffee::getName) // 假设Coffee类中有getName()方法
                    .collect(Collectors.joining(", "));

            return new CoffeeService.BookingDetails(
                    coffeeOrder.getId() + "",
                    coffeeOrder.getCustomer(),
                    items,
                    coffeeOrder.getCreateTime(),
                    coffeeOrder.getState(),
                    coffeeOrder.getDiscount(),
                    coffeeOrder.getTotal(),
                    coffeeOrder.getWaiter(),
                    coffeeOrder.getBarista()
            );
        } else {
            throw new RuntimeException("Coffee order is null");
        }
    }

    @Tool(description = "根据客人名字和咖啡名，生成新订单")
    public CoffeeOrder orderingCoffee(BookingCoffeeRequest request) {
//        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: 创建订单 %s for %s ...%n", request.customer, request.items);
//        System.out.println("==========================================================================================");
        NewOrderRequest newOrderRequest = NewOrderRequest.builder().customer(request.customer).items(request.items).build();

        CoffeeOrder coffeeOrder = restClient.post()
                .uri("/waiter-service/order/")
                .contentType(MediaType.APPLICATION_JSON) // 设置请求头
                .body(newOrderRequest) // 设置请求体
                .retrieve().body(CoffeeOrder.class);
        return coffeeOrder;

//        return coffeeOrderService.create(newOrderRequest);
    }

    @Tool(description = "根据客人名字和订单编号，取消订单")
    public CoffeeOrder cancelOrder(String bookingNumber, String customerName) {
//        System.out.println("==========================================================================================");
//        System.out.printf("[Tool]: 取消订单 %s for %s ...%n", bookingNumber, customerName);
//        System.out.println("==========================================================================================");
//        return coffeeOrderService.updateState(Long.parseLong(bookingNumber), OrderStateRequest.builder().state(OrderState.CANCELLED).build());
        return restClient.put()
                .uri("/waiter-service/order/{id}", bookingNumber)
                .contentType(MediaType.APPLICATION_JSON) // 设置请求头
                .body(OrderStateRequest.builder().state(OrderState.CANCELLED).build()) // 设置请求体
                .retrieve().body(CoffeeOrder.class);
    }

    public static void main(String[] args) {
        CoffeeService client = new CoffeeService();
        System.out.println(client.getOrderDetails("1", "QiXin"));
    }
}
