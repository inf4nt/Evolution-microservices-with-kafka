package com.evolution.user.stream;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserStateEvent;
import com.evolution.user.event.UserUpdateEvent;
import com.evolution.user.layer.query.repository.UserRepository;
import com.evolution.user.service.UserEventBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.concurrent.TimeUnit;

@EnableBinding(UserStateProcessor.class)
public class UserStateProcessorStream {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener
    @SendTo(UserStateProcessor.OUTPUT_RESULT)
    public KStream<String, UserStateEvent> generateStateKStream(@Input(UserStateProcessor.INPUT_CREATE) KStream<String, UserCreateEvent> createStream,
                                                                @Input(UserStateProcessor.INPUT_UPDATE) KStream<String, UserUpdateEvent> updateStream) {

        Serde<UserCreateEvent> serdeCreate = new JsonSerde<>(UserCreateEvent.class, objectMapper);

        Serde<UserUpdateEvent> serdeUpdate = new JsonSerde<>(UserUpdateEvent.class, objectMapper);

        return createStream.leftJoin(updateStream, (create, update) -> UserEventBuilder.buildState(create, update), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                Serdes.String(),
                serdeCreate,
                serdeUpdate
        );
    }
}
