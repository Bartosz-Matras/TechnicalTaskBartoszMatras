package com.ocadogroup.entity;

public class Picker {

    private final String name;
    private final String pickingStartTime;
    private String pickingCurrentTime;
    private final String pickingEndTime;

    public Picker(String name, String pickingStartTime, String pickingCurrentTime, String pickingEndTime) {
        this.name = name;
        this.pickingStartTime = pickingStartTime;
        this.pickingCurrentTime = pickingCurrentTime;
        this.pickingEndTime = pickingEndTime;
    }

    public String getName() {
        return name;
    }

    public String getPickingStartTime() {
        return pickingStartTime;
    }

    public String getPickingCurrentTime() {
        return pickingCurrentTime;
    }

    public String getPickingEndTime() {
        return pickingEndTime;
    }

    public void setPickingCurrentTime(String pickingCurrentTime) {
        this.pickingCurrentTime = pickingCurrentTime;
    }
}
