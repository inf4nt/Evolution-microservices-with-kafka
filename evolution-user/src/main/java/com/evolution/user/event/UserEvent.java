package com.evolution.user.event;

public interface UserEvent {

    default String getTopic() {
        return this.getClass().getSimpleName() + "Topic";
    }
}
