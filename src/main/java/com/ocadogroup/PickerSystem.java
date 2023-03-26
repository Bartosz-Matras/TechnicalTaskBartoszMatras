package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

import static com.ocadogroup.GsonDeserialization.*;

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

//        PickerThread pickerThread = new PickerThread(store.getPickers().get(0), store.getPickingStartTime(), store.getPickingEndTime(), orders);
//        Thread thread = new Thread(pickerThread);
//        thread.start();

        for (String pickerName : store.getPickers()) {
            PickerThread pickerThread = new PickerThread(pickerName, store.getPickingStartTime(), store.getPickingEndTime(), orders);
            Thread thread = new Thread(pickerThread);
            thread.start();
        }
    }

}
