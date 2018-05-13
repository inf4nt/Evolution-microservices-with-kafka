package com.evolution.library.core.v5;

public interface Event<Key, MC extends Content, RT extends RequestType, ES extends EventStatus> extends Message<Key, MC, RT> {

    ES getEventStatus();

    default String getStore() {
        return MessageService.getStore(this.getClass());
    }
}
