package com.ocadogroup.entity;

import java.util.List;

public class Store {

    private List<String> pickers;
    private String pickingStartTime;
    private String pickingEndTime;

    public Store(List<String> pickers, String pickingStartTime, String pickingEndTime) {
        this.pickers = pickers;
        this.pickingStartTime = pickingStartTime;
        this.pickingEndTime = pickingEndTime;
    }

    public List<String> getPickers() {
        return pickers;
    }

    public String getPickingStartTime() {
        return pickingStartTime;
    }

    public String getPickingEndTime() {
        return pickingEndTime;
    }

    @Override
    public String toString() {
        return "Store{" +
                "pickers=" + pickers +
                ", pickingStartTime='" + pickingStartTime + '\'' +
                ", pickingEndTime='" + pickingEndTime + '\'' +
                '}';
    }
}
