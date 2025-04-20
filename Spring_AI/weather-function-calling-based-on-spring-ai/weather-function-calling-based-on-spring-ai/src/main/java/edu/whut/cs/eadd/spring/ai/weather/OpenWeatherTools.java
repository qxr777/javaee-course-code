package edu.whut.cs.eadd.spring.ai.weather;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenWeatherTools {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = System.getenv("OPEN_WEATHER_API_KEY"); // 请替换为你的实际 API key

    private final RestClient restClient;

    public OpenWeatherTools() {
        this.restClient = RestClient.create();
    }

    @Tool(description = "Get weather forecast for a specific location")
    public String getWeather(String location) {
        // 构建请求 URL
        String url = String.format("%s?q=%s&appid=%s&units=metric&lang=zh_cn", API_URL, location, API_KEY);

        // 使用 RestClient 发送请求并获取响应
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }

}
