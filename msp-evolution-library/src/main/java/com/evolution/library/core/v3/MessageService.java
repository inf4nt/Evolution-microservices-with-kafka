package com.evolution.library.core.v3;

import com.evolution.library.core.v3.global.GlobalMessage;

import java.util.UUID;

public class MessageService {

    public static String getMessageType(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getFeed(Class<? extends Message> clazz) {
        return clazz.getSimpleName() + "Feed";
    }

    public static String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
