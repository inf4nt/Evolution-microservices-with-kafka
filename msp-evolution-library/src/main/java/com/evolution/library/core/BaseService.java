package com.evolution.library.core;

public class BaseService {

    public static String getFeed(Class<? extends Base> base) {
        return base.getSimpleName() + "Feed";
    }

//    public static String getStore(Class<? extends Base> base) {
//        return base.getSimpleName() + "Store";
//    }
}
