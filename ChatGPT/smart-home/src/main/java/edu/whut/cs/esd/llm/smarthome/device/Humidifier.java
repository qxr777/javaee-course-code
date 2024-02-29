package edu.whut.cs.esd.llm.smarthome.device;

import org.springframework.stereotype.Component;

@Component
public class Humidifier {

    private double expectedHumidity;

    public double getExpectedHumidity() {
        return expectedHumidity;
    }

    public void setExpectedHumidity(double expectedHumidity) {
        this.expectedHumidity = expectedHumidity;
    }

    public void run() {
        System.out.println("Humidifier is running.");
    }

    public void stop() {
        System.out.println("Humidifier is stopping.");
    }
}
