package edu.whut.cs.esd.llm.smarthome.device;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Thermometer {
    private double temperature;
    private double humidity;
    private double pressure;

    public double getTemperature() {
       // 创建一个Random对象和一个Double对象用于生成随机浮点数
        Random random = new Random();
        double minTemperature = 10.0;
        double maxTemperature = 19.0;

        // 生成并输出随机温度
        temperature = minTemperature + (maxTemperature - minTemperature) * random.nextDouble();
        System.out.println("[Thermometer]: Start measuring temperature" );
        return temperature;
    }

    public double getHumidity() {
        // 生成随机湿度
        humidity = (Math.random() * 100) + 10;
        System.out.println("[Thermometer]: Start measuring humidity" );
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

}
