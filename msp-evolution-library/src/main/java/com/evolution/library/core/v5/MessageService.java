package com.evolution.library.core.v5;


import java.util.UUID;

public class MessageService {

    public static String getFeed(Class<? extends Message> clazz) {
        return clazz.getSimpleName() + "Feed";
    }

    public static String getStore(Class<? extends Message> clazz) {
        return clazz.getSimpleName() + "Store";
    }

    public static String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
