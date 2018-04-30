package com.evolution.library.core;

public interface Base<Key> {

    Key getKey();

    default String getFeed() {
        return BaseService.getFeed(this.getClass());
    }

    default String getStore() {
        return BaseService.getStore(this.getClass());
    }
}
