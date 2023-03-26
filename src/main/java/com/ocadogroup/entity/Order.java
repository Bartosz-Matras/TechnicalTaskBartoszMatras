package com.ocadogroup.entity;

public class Order {

    private String orderId;
    private String orderValue;
    private String pickingTime;
    private String completeBy;

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
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderValue='" + orderValue + '\'' +
                ", pickingTime='" + pickingTime + '\'' +
                ", completeBy='" + completeBy + '\'' +
                '}';
    }

}
