package edu.whut.cs.javaee.spring.cloud.zipkinwaiterservice.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Customer {
    String NOTIFY_ORDERS = "notifyOrders";

    @Output(NOTIFY_ORDERS)
    MessageChannel notification();
}
