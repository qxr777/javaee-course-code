package edu.whut.cs.esd.llm.smarthome.device;

import org.springframework.stereotype.Component;

@Component
public class AirConditioner extends BaseDevice{

    private double expectedTemperature;

    public void setExpectedTemperature(double expectedTemperature) {
        if (getState() == DeviceState.STOPPED) {
            start();
        }
        this.expectedTemperature = expectedTemperature;
    }

    public double getExpectedTemperature() {
        return expectedTemperature;
    }

}
