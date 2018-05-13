package com.evolution.library.core.v5;

public interface Message<Key, MC extends Content, RT extends RequestType> extends Base<Key> {

    String getCorrelation();

    MC getContent();

    RT getType();

    default String getFeed() {
        return MessageService.getFeed(this.getClass());
    }
}
