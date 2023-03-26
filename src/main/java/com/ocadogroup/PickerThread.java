package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Picker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.ocadogroup.DateFormatter.*;

public class PickerThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(PickerThread.class);

    private static final List<Picker> pickers = new ArrayList<>();
    private final Picker currentPicker;
    private final List<Order> orders;
    private int first = 0;

    public PickerThread(String pickerName, String pickingStartTime, String pickingEndTime, List<Order> orders) {
        this.currentPicker = new Picker(pickerName, initializeDate(pickingStartTime),
                initializeDate(pickingStartTime), initializeDate(pickingEndTime));
        pickers.add(this.currentPicker);
        this.orders = orders;
    }

    @Override
    public void run() {

        while (this.currentPicker.getPickingCurrentTime().compareTo(this.currentPicker.getPickingEndTime()) < 1) {
            Order orderToProcess = getOrderForPicker();
            if (orderToProcess == null) {
                break;
            }
            System.out.println(this.currentPicker.getName() + " " + orderToProcess.getOrderId() + " " + this.currentPicker.getPickingCurrentTime());
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
            for (Picker picker : pickers) {
                if (!this.currentPicker.getName().equals(picker.getName()) &&
                        numberOfMinutesBetweenTwoDates(this.currentPicker.getPickingCurrentTime(), picker.getPickingCurrentTime()) < 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        logger.error("Interrupted exception {0}", e);
                    }
                }
            }
            for (Order order : orders) {
                String modifiedDate = updateDate(this.currentPicker.getPickingCurrentTime(),
                        getMinutesFromISOFormat(order.getPickingTime()));
                String completeByDate = initializeDate(order.getCompleteBy());

                long currentNumberDiff = numberOfMinutesBetweenTwoDates(modifiedDate, completeByDate);
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
            if (first == 0 && getMinutesFromISOFormat(order.getPickingTime()) <= pickingTime){
                betterOrder = order;
            }else if (first != 0 && getMinutesFromISOFormat(order.getPickingTime()) >= pickingTime) {
                betterOrder = order;
            }
        }
        first++;
        return betterOrder;
    }
}
