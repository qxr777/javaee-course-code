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
        System.out.println("[Thermometer]: Getting temperature..." + thermometer.getTemperature());
        System.out.println("==========================================================================================");

        return thermometer.getTemperature();
    }

    @Tool
    public double getHumidity() {
        System.out.println("==========================================================================================");
        System.out.println("[Thermometer]: Getting humidity..." + thermometer.getHumidity());
        System.out.println("==========================================================================================");

        return thermometer.getHumidity();
    }

    @Tool
    public double setExpectedTemperature(double expectedTemperature) {
        System.out.println("==========================================================================================");
        System.out.printf("[AirConditioner]: Setting expected temperature to %f...%n", expectedTemperature);
        System.out.println("==========================================================================================");

        airConditioner.setExpectedTemperature(expectedTemperature);
        return expectedTemperature;
    }

    @Tool
    public double setExpectedHumidity(double expectedHumidity) {
        System.out.println("==========================================================================================");
        System.out.printf("[Humidifier]: Setting expected humidity to %f...%n", expectedHumidity);
        System.out.println("==========================================================================================");

        humidifier.setExpectedHumidity(expectedHumidity);
        return expectedHumidity;
    }


    @Tool
    public void startHumidifier() {
        System.out.println("==========================================================================================");
        System.out.println("[Humidifier]: Starting humidifier...");
        System.out.println("==========================================================================================");

        humidifier.start();
    }

    @Tool
    public void stopHumidifier() {
        System.out.println("==========================================================================================");
        System.out.println("[Humidifier]: Stopping humidifier...");
        System.out.println("==========================================================================================");

        humidifier.stop();
    }

    @Tool
    public void startAirConditioner() {
        System.out.println("==========================================================================================");
        System.out.println("[AirConditioner]: Starting air conditioner...");
        System.out.println("==========================================================================================");

        airConditioner.start();
    }

    @Tool
    public void stopAirConditioner() {
        System.out.println("==========================================================================================");
        System.out.println("[AirConditioner]: Stopping air conditioner...");
        System.out.println("==========================================================================================");

        airConditioner.stop();
    }

}