package edu.whut.cs.esd.llm.smarthome.device;

import org.springframework.stereotype.Component;

@Component
public class Humidifier extends BaseDevice{

    private double expectedHumidity;

    public double getExpectedHumidity() {
        return expectedHumidity;
    }

    public void setExpectedHumidity(double expectedHumidity) {
        if (getState() == DeviceState.STOPPED) {
            start();
        }
        this.expectedHumidity = expectedHumidity;
    }

}
