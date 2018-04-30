package com.evolution.library.core;

public interface Base<Key> {

    Key getKey();

    default String getFeed() {
        return BaseService.getFeed(this.getClass());
    }
}
