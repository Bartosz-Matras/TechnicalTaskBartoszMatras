package com.ocadogroup;

import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Store;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class GsonDeserializationTest {

    @Test
    void deserializeOrdersShouldReturnListOfOrders() {
        //given
        String orderFile = "C:\\Users\\barto\\Downloads\\self-test-data\\advanced-allocation\\orders.json";

        //when
        List<Order> orders = GsonDeserialization.deserializeOrders(orderFile);

        //then
        assertThat(orders, hasSize(7));
        assertThat(orders.get(0), instanceOf(Order.class));
    }

    @Test
    void deserializeStoreShouldReturnStoreClass() {
        //given
        String storeFile = "C:\\Users\\barto\\Downloads\\self-test-data\\advanced-allocation\\store.json";

        //when
        Store store = GsonDeserialization.deserializeStore(storeFile);

        //then
        assertThat(store, instanceOf(Store.class));
    }

}
