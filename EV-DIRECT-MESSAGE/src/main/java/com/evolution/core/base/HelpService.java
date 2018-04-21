package com.evolution.core.base;

public class HelpService {

    public static String getFeed(Class<? extends Base> clazz) {
        return clazz.getSimpleName() + "Feed";
    }
}
