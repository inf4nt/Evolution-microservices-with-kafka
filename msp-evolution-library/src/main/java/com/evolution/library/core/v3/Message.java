package com.evolution.library.core.v3;

public interface Message<Key> extends Base<Key> {

    String getCorrelation();

    default String getFeed() {
        return MessageService.getFeed(this.getClass());
    }
}
