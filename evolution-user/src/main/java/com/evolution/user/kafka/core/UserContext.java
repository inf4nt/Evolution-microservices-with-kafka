package com.evolution.user.kafka.core;

import com.evolution.user.event.UserStateEvent;
import com.evolution.user.kafka.core.Context;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonSerde;

public class UserContext implements Context<String, UserStateEvent, Serdes.StringSerde, JsonSerde> {

    @Override
    public Class<? extends Serdes.StringSerde> getSerdeKey() {
        return Serdes.StringSerde.class;
    }

    @Override
    public Class<? extends JsonSerde> getSerdeValue() {
        return JsonSerde.class;
    }

    @Override
    public Class<String> getKeyClass() {
        return String.class;
    }

    @Override
    public Class<UserStateEvent> getStateEventClass() {
        return UserStateEvent.class;
    }
}
