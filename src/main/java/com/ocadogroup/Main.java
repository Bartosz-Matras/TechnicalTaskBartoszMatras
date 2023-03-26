package com.ocadogroup;

import org.apache.logging.log4j.*;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        if(args.length != 2) {
            logger.info("You need to specifies two parameters");
        }else {
            PickerSystem pickerSystem = new PickerSystem();
            String orderFile = args[0].endsWith("orders.json") ? args[0] : args[1];
            String storeFile = args[0].endsWith("store.json") ? args[0] : args[1];
            pickerSystem.start(orderFile, storeFile);
        }
    }
}
