package com.evolution.direct.message.core;

public interface Base<Key> {

    Key getKey();

    default String getFeed() {
        return HelpService.getFeed(this.getClass());
    }
}
