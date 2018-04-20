package com.evolution.user.kafka.core;

import org.apache.kafka.common.serialization.Serde;

public interface Context<Key, StateEvent extends Event<Key>, SerdeKey extends Serde, SerdeValue extends Serde> {

    Class<? extends SerdeKey> getSerdeKey();

    Class<? extends SerdeValue> getSerdeValue();

    Class<Key> getKeyClass();

    Class<StateEvent> getStateEventClass();

    default String getStateEventTopic() {
        return getStateEventClass().getSimpleName() + "Feed";
    }
}
