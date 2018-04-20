package com.evolution.user.kafka.core;

public class EventService {

    public static String getTopicName(Class<? extends Event> clazz) {
        return clazz.getSimpleName() + "Feed";
    }

    public static String getStoreName(Class<? extends Event> clazz) {
        return clazz.getSimpleName() + "Store";
    }
}
