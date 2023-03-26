package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Scheduler;
import com.ocadogroup.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Collections;
import java.util.List;

import static com.ocadogroup.GsonDeserialization.deserializeOrders;
import static com.ocadogroup.GsonDeserialization.deserializeStore;
import static com.ocadogroup.PickerThread.getSchedulers;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class PickerThreadTest {

    private List<Order> orders;
    private Store store;

    @BeforeEach
    void initializeOrdersAndStore() {
        String orderFile = "C:\\Users\\barto\\Downloads\\self-test-data\\advanced-allocation\\orders.json";
        String storeFile = "C:\\Users\\barto\\Downloads\\self-test-data\\advanced-allocation\\store.json";
        orders = Collections.synchronizedList(deserializeOrders(orderFile));
        store = deserializeStore(storeFile);
    }

    @Test
    void threadsShouldReturnOrderedListByDateAndThenName() {
        //given
        List<String> expectedSchedulers = List.of(
                "P1 order-1 09:00",
                "P2 order-2 09:00",
                "P1 order-5 09:15",
                "P2 order-4 09:30",
                "P2 order-3 09:45",
                "P2 order-6 10:00",
                "P1 order-7 10:15"
        );
        List<String> pickerNames = List.of("P1", "P2");
        for(String pickerName : pickerNames) {
            PickerThread pickerThread = new PickerThread(pickerName, store.getPickingStartTime(), store.getPickingEndTime(), orders);
            Thread thread = new Thread(pickerThread);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        //when
        List<Scheduler> schedulers = getSchedulers();

        //then
        assertThat(schedulers, hasSize(7));
        assertThat(schedulers.get(0), instanceOf(Scheduler.class));
        assertThat(schedulers.get(0).toString(), equalTo(expectedSchedulers.get(0)));
        assertThat(schedulers.get(1).toString(), equalTo(expectedSchedulers.get(1)));
        assertThat(schedulers.get(2).toString(), equalTo(expectedSchedulers.get(2)));
        assertThat(schedulers.get(3).toString(), equalTo(expectedSchedulers.get(3)));
        assertThat(schedulers.get(4).toString(), equalTo(expectedSchedulers.get(4)));
        assertThat(schedulers.get(5).toString(), equalTo(expectedSchedulers.get(5)));
        assertThat(schedulers.get(6).toString(), equalTo(expectedSchedulers.get(6)));
    }
}







