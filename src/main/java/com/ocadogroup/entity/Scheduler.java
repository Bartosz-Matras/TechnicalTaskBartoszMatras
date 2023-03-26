package com.ocadogroup.entity;

public class Scheduler {

    private final String name;
    private final String orderNumber;
    private final String date;

    public Scheduler(String name, String orderNumber, String date) {
        this.name = name;
        this.orderNumber = orderNumber;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return name + " " + orderNumber + " " + date;
    }
}
