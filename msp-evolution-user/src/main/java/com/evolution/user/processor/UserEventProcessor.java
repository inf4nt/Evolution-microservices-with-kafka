package com.evolution.user.processor;

import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.UserEvent;
import com.evolution.user.core.UserEventHandler;
import com.evolution.user.core.UserState;
import com.evolution.user.core.common.UserEventStatus;
import com.evolution.user.processor.bindings.EventProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.validation.Valid;

@EnableBinding(EventProcessor.class)
public class UserEventProcessor {

    private final UserEventHandler userEventHandler;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserEventProcessor(UserEventHandler userEventHandler, ObjectMapper objectMapper) {
        this.userEventHandler = userEventHandler;
        this.objectMapper = objectMapper;
    }

    @StreamListener(EventProcessor.INPUT)
    @SendTo(EventProcessor.OUTPUT)
    public KStream<String, UserState> process(@Valid KStream<String, UserEvent> input) {
        System.out.println("UserEventStreamProcessor");
        final Serde<UserState> userStateEventSerde = new JsonSerde<>(UserState.class, objectMapper);
        final Serde<UserEvent> userEventSerde = new JsonSerde<>(UserEvent.class, objectMapper);

        return input
                .filter((k, v) -> v.getEventStatus() == UserEventStatus.PROGRESS)
                .groupByKey(Serialized.with(Serdes.String(), userEventSerde))
                .aggregate(UserState::new,
                        (key, event, state) -> userEventHandler.handle(event, state),
                        Materialized
                                .<String, UserState, KeyValueStore<Bytes, byte[]>>as(MessageService.getStore(UserState.class))
                                .withKeySerde(Serdes.String())
                                .withValueSerde(userStateEventSerde))
                .toStream();
    }
}
