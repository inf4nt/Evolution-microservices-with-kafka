package com.evolution.user.processor.event;

public interface UserEvent {

    default String getTopic() {
        return this.getClass().getSimpleName() + "Topic";
    }
}
