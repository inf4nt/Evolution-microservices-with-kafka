package com.evolution.direct.message.event;

public interface MessageEvent {

    default String getTopic() {
        return this.getClass().getSimpleName() + "Topic";
    }
}
