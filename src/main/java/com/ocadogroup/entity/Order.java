package com.ocadogroup.entity;

public class Order {

    private final String orderId;
    private final String orderValue;
    private final String pickingTime;
    private final String completeBy;

    public Order(String orderId, String orderValue, String pickingTime, String completeBy) {
        this.orderId = orderId;
        this.orderValue = orderValue;
        this.pickingTime = pickingTime;
        this.completeBy = completeBy;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public String getPickingTime() {
        return pickingTime;
    }

    public String getCompleteBy() {
        return completeBy;
    }

}
