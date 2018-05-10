package com.evolution.library.core.v2;

import java.util.UUID;

public class EventSourcingService {

    public static String getFeed(Class<? extends Base> base) {
        return base.getSimpleName() + "Feed";
    }

    public static String generateRundom() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
