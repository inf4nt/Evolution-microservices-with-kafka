package com.evolution.user.processor;

import com.evolution.user.core.common.UserEventStatus;
import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.UserEvent;
import com.evolution.user.core.UserEventHandler;
import com.evolution.user.core.UserStateEvent;
import com.evolution.user.processor.bindings.UserEventProcessor;
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

@EnableBinding(UserEventProcessor.class)
public class UserEventStreamProcessor {

    private final UserEventHandler userEventHandler;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserEventStreamProcessor(UserEventHandler userEventHandler,
                                    ObjectMapper objectMapper) {
        this.userEventHandler = userEventHandler;
        this.objectMapper = objectMapper;
    }

    @StreamListener(UserEventProcessor.INPUT)
    @SendTo(UserEventProcessor.OUTPUT)
    public KStream<String, UserStateEvent> process(KStream<String, UserEvent> input) {
        System.out.println("UserEventStreamProcessor");
        final Serde<UserStateEvent> userStateEventSerde = new JsonSerde<>(UserStateEvent.class, objectMapper);
        final Serde<UserEvent> userEventSerde = new JsonSerde<>(UserEvent.class, objectMapper);

        return input
                .filter((k, v) -> v.getUserEventStatus() == UserEventStatus.Progress)
                .groupByKey(Serialized.with(Serdes.String(), userEventSerde))
                .aggregate(UserStateEvent::new,
                        (key, event, state) -> userEventHandler.handle(event, state),
                        Materialized
                                .<String, UserStateEvent, KeyValueStore<Bytes, byte[]>>as(MessageService.getStore(UserStateEvent.class))
                                .withKeySerde(Serdes.String())
                                .withValueSerde(userStateEventSerde))
                .toStream();
    }
}
