package com.evolution.library.core;

public class BaseService {

    public static String getFeed(Class<? extends Base> baseClass) {
        return baseClass.getSimpleName() + "Feed";
    }

    public static String getStore(Class<? extends Base> baseClass) {
        return baseClass.getSimpleName() + "Store";
    }
}
