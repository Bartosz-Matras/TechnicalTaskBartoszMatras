package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Picker;
import com.ocadogroup.entity.Scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ocadogroup.DateFormatter.*;

public class PickerThread implements Runnable {

    private static final List<Scheduler> schedulers = new ArrayList<>();
    private final Picker currentPicker;
    private final List<Order> orders;
    private int first = 0;

    public PickerThread(String pickerName, String pickingStartTime, String pickingEndTime, List<Order> orders) {
        this.currentPicker = new Picker(pickerName, pickingStartTime,
                pickingStartTime, pickingEndTime);
        this.orders = orders;
    }

    @Override
    public void run() {
        while (this.currentPicker.getPickingCurrentTime().compareTo(this.currentPicker.getPickingEndTime()) < 1) {
            Order orderToProcess = getOrderForPicker();
            if (orderToProcess == null) {
                break;
            }
            schedulers.add(new Scheduler(this.currentPicker.getName(), orderToProcess.getOrderId(),
                    this.currentPicker.getPickingCurrentTime()));
            this.currentPicker.setPickingCurrentTime(updateDate(this.currentPicker.getPickingCurrentTime(),
                    getMinutesFromISOFormat(orderToProcess.getPickingTime())));
        }
    }


    private Order getOrderForPicker() {
        if (orders.isEmpty()) {
            return null;
        }
        Order orderToProcess;
        List<Order> ordersToProcess = new ArrayList<>();

        long currentSmallDate = numberOfMinutesBetweenTwoDates(this.currentPicker.getPickingStartTime(),
                this.currentPicker.getPickingEndTime());
        synchronized (orders) {
            for (Order order : orders) {
                String modifiedDate = updateDate(this.currentPicker.getPickingCurrentTime(),
                        getMinutesFromISOFormat(order.getPickingTime()));
                long currentNumberDiff = numberOfMinutesBetweenTwoDates(modifiedDate, order.getCompleteBy());
                if (currentNumberDiff <= currentSmallDate && currentNumberDiff >= 0)  {
                    currentSmallDate = currentNumberDiff;
                    ordersToProcess.add(order);
                }
            }
            orderToProcess = getShorterOrder(ordersToProcess);
            orders.remove(orderToProcess);
        }
        return orderToProcess;
    }

    private Order getShorterOrder(List<Order> ordersToProcess) {
        if (ordersToProcess.isEmpty()) {
            return null;
        }
        Order betterOrder = ordersToProcess.get(0);
        long pickingTime = getMinutesFromISOFormat(ordersToProcess.get(0).getPickingTime());

        for (Order order : ordersToProcess) {
            if ((first == 0 && getMinutesFromISOFormat(order.getPickingTime()) <= pickingTime)
                    || (first != 0 && getMinutesFromISOFormat(order.getPickingTime()) >= pickingTime)){
                betterOrder = order;
                pickingTime = getMinutesFromISOFormat(order.getPickingTime());
            }
        }
        first++;
        return betterOrder;
    }

    public static List<Scheduler> getSchedulers() {
        schedulers.sort(Comparator.comparing(Scheduler::getDate).thenComparing(Scheduler::getName));
        return schedulers;
    }

}
