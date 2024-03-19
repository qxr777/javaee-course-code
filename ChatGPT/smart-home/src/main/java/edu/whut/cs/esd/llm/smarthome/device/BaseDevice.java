package edu.whut.cs.esd.llm.smarthome.device;

public class BaseDevice {
    private DeviceState state;

    public BaseDevice()
    {
        this.state = DeviceState.STOPPED;
    }
    public void start()
    {
        this.state = DeviceState.RUNNING;
    }

    public void stop()
    {
        this.state = DeviceState.STOPPED;
    }

    public DeviceState getState() {
        return state;
    }
}
