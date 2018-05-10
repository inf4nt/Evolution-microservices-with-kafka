package com.evolution.library.core.v2;

public interface EventAggregator<Key> extends EventSourcingMessage<Key> {

    String getEventType();

    String getCommandNumber();
}
