package com.ocadogroup.entity;

import java.util.List;

public class Store {

    private final List<String> pickers;
    private final String pickingStartTime;
    private final String pickingEndTime;

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
}
