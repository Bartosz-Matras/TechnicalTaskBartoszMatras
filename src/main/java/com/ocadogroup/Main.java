package com.ocadogroup;

public class Main {


    public static void main(String[] args) {

        if(args.length != 2) {
            System.out.println("You need to specifies two parameters");
        }else {
            PickerSystem pickerSystem = new PickerSystem();
            String orderFile = args[0].endsWith("orders.json") ? args[0] : args[1];
            String storeFile = args[0].endsWith("store.json") ? args[0] : args[1];
            pickerSystem.start(orderFile, storeFile);
        }
    }
}
