package com.evolution.user.kafka.core;

import static com.evolution.user.kafka.core.EventService.getTopicName;

public interface Event<Key> extends Base<Key> {

    default String getTopic() {
        return getTopicName(this.getClass());
    }
}
