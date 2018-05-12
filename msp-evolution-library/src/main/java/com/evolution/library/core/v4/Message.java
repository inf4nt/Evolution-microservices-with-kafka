package com.evolution.library.core.v4;

public interface Message<Key> extends Base<Key> {

    String getCorrelation();

    default String getFeed() {
        return MessageService.getFeed(this.getClass());
    }

    default String getStore() {
        return MessageService.getStore(this.getClass());
    }
}
