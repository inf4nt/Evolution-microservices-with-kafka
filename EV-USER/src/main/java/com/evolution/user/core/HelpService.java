package com.evolution.user.core;

public class HelpService {

    public static String getFeed(Class<? extends Base> clazz) {
        return clazz.getSimpleName() + "Feed";
    }
}
