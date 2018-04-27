package com.evolution.direct.message.core;

public class HelpService {

    public static String getFeed(Class<? extends Base> clazz) {
        return clazz.getSimpleName() + "Feed";
    }
}
