package com.evolution.msp.user.coreold;

public class EventService {

    public static String getTopic(Class<? extends Event> clazz) {
        return clazz.getSimpleName() + "Feed";
    }

    public static String getStore(Class<? extends State> clazz) {
        return clazz.getSimpleName() + "Store";
    }
}
