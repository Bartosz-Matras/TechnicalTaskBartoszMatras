package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Scheduler;
import com.ocadogroup.entity.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static com.ocadogroup.GsonDeserialization.*;
import static com.ocadogroup.PickerThread.getSchedulers;

public class PickerSystem {

    private static final Logger logger = LogManager.getLogger(PickerSystem.class);

    private List<Order> orders;
    private Store store;

    public void start(String orderFile, String storeFile) {

        orders = Collections.synchronizedList(deserializeOrders(orderFile));
        store = deserializeStore(storeFile);

        if (orders.isEmpty() || store == null) {
            logger.info("Orders are empty or store is null");
            return;
        }

        for (String pickerName : store.getPickers()) {
            PickerThread pickerThread = new PickerThread(pickerName, store.getPickingStartTime(), store.getPickingEndTime(), orders);
            Thread thread = new Thread(pickerThread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("Interrupted exception {0}", e);
                Thread.currentThread().interrupt();
            }
        }

        for (Scheduler scheduler : getSchedulers()) {
            System.out.println(scheduler);
        }
    }

}
