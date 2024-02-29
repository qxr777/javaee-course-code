package edu.whut.cs.esd.llm.smarthome.device;

import org.springframework.stereotype.Component;

@Component
public class AirConditioner {
    private double expectedTemperature;

    public void setExpectedTemperature(double expectedTemperature) {
        this.expectedTemperature = expectedTemperature;
    }

    public double getExpectedTemperature() {
        return expectedTemperature;
    }

    public void run() {
        System.out.println("AirConditioner is running.");
    }

    public void stop() {
        System.out.println("AirConditioner is stopping.");
    }
}
