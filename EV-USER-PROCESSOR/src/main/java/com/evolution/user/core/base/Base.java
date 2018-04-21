package com.evolution.user.core.base;

public interface Base<Key> {

    Key getKey();

    default String getFeed() {
        return HelpService.getFeed(this.getClass());
    }
}
