package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Scheduler;
import com.ocadogroup.entity.Store;

import java.util.Collections;
import java.util.List;

import static com.ocadogroup.GsonDeserialization.*;
import static com.ocadogroup.PickerThread.getSchedulers;

public class PickerSystem {


    private List<Order> orders;
    private Store store;

    public void start(String orderFile, String storeFile) {

        orders = Collections.synchronizedList(deserializeOrders(orderFile));
        store = deserializeStore(storeFile);

        if (orders.isEmpty() || store == null) {
            System.out.println("Orders are empty or store is null");
            return;
        }

        for (String pickerName : store.getPickers()) {
            PickerThread pickerThread = new PickerThread(pickerName, store.getPickingStartTime(), store.getPickingEndTime(), orders);
            Thread thread = new Thread(pickerThread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        for (Scheduler scheduler : getSchedulers()) {
            System.out.println(scheduler);
        }
    }

}
