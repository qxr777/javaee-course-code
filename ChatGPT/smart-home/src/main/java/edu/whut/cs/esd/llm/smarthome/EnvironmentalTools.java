package edu.whut.cs.esd.llm.smarthome;

import dev.langchain4j.agent.tool.Tool;
import edu.whut.cs.esd.llm.smarthome.device.AirConditioner;
import edu.whut.cs.esd.llm.smarthome.device.Humidifier;
import edu.whut.cs.esd.llm.smarthome.device.Thermometer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentalTools {

    @Autowired
    private Thermometer thermometer;

    @Autowired
    private AirConditioner airConditioner;

    @Autowired
    private Humidifier humidifier;

    @Tool
    public double getTemperature() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Getting temperature..." + thermometer.getTemperature());
        System.out.println("==========================================================================================");

        return thermometer.getTemperature();
    }

    @Tool
    public double getHumidity() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Getting humidity..." + thermometer.getHumidity());
        System.out.println("==========================================================================================");

        return thermometer.getHumidity();
    }

    @Tool
    public double setExpectedTemperature(double expectedTemperature) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: Setting expected temperature to %f...%n", expectedTemperature);
        System.out.println("==========================================================================================");

        airConditioner.setExpectedTemperature(expectedTemperature);
        return expectedTemperature;
    }

    @Tool
    public double setExpectedHumidity(double expectedHumidity) {
        System.out.println("==========================================================================================");
        System.out.printf("[Tool]: Setting expected humidity to %f...%n", expectedHumidity);
        System.out.println("==========================================================================================");

        humidifier.setExpectedHumidity(expectedHumidity);
        return expectedHumidity;
    }

    @Tool
    public void startHumidifier() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Starting humidifier...");
        System.out.println("==========================================================================================");

        humidifier.run();
    }

    @Tool
    public void stopHumidifier() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Stopping humidifier...");
        System.out.println("==========================================================================================");

        humidifier.stop();
    }

    @Tool
    public void startAirConditioner() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Starting air conditioner...");
        System.out.println("==========================================================================================");

        airConditioner.run();
    }

    @Tool
    public void stopAirConditioner() {
        System.out.println("==========================================================================================");
        System.out.println("[Tool]: Stopping air conditioner...");
        System.out.println("==========================================================================================");

        airConditioner.stop();
    }

}