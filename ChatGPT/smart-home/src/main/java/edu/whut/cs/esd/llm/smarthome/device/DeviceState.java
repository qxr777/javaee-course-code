package edu.whut.cs.esd.llm.smarthome.device;

public enum DeviceState {

    /**
     * 设备运行正常
     */
    RUNNING,

    /**
     * 设备已停止
     */
    STOPPED,

    /**
     * 设备正在暂停
     */
    PAUSED,

    /**
     * 设备正在启动
     */
    STARTING,

    /**
     * 设备正在停止
     */
    STOPPING,

    /**
     * 设备出现故障
     */
    FAILED;

    public String getDesc() {
        switch (this) {
            case RUNNING:
                return "运行中";
            case STOPPED:
                return "已停止";
            case PAUSED:
                return "已暂停";
            case STARTING:
                return "正在启动";
            case STOPPING:
                return "正在停止";
            case FAILED:
                return "故障";
            default:
                return "";
        }
    }
}

