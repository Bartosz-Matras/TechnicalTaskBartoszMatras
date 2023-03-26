package com.ocadogroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ocadogroup.entity.Order;
import com.ocadogroup.entity.Store;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GsonDeserialization {


    private static final Gson gson = new Gson();

    private GsonDeserialization() {}

    public static List<Order> deserializeOrders(String orderFile) {
        List<Order> orders;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(orderFile));
            orders = gson.fromJson(reader, new TypeToken<List<Order>>(){}.getType());
            return orders;
        } catch (FileNotFoundException e) {
            System.out.println("File" + orderFile + " not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file " + orderFile);
        }
        return new ArrayList<>();
    }

    public static Store deserializeStore(String storeFile) {
        Store store;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(storeFile));
            store = gson.fromJson(reader, new TypeToken<Store>(){}.getType());
            return store;
        } catch (FileNotFoundException e) {
            System.out.println("File" + storeFile + " not found.");
        } catch (IOException e) {
            System.out.println("Cannot read file " + storeFile);
        }
        return null;
    }

}
