package com.evolution.user.core.topology;


import com.evolution.core.event.UserCreateEvent;
import com.evolution.core.state.UserState;
import com.evolution.core.state.UserUsernameKeyState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static com.evolution.library.core.BaseService.getFeed;


@Component
public class UserStateTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserStateTopology(ObjectMapper objectMapper) {
        super(UserStateTopology.class.getSimpleName(), "user-processor", Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
        Serde<UserCreateEvent> userCreateEventSerde = new JsonSerde<>(UserCreateEvent.class, objectMapper);
        Serde<UserUsernameKeyState> userStateKeyUsernameSerde = new JsonSerde<>(UserUsernameKeyState.class, objectMapper);

        final KStream<String, UserCreateEvent> userCreateEventKStream = builder
                .stream(getFeed(UserCreateEvent.class), Consumed.with(Serdes.String(), userCreateEventSerde));

        final KStream<String, UserUsernameKeyState> userUsernameKeyKStream =
                userCreateEventKStream.map((k, v) -> new KeyValue<>(v.getUserCreateCommand().getUsername(), UserUsernameKeyState.builder().build()));

        userUsernameKeyKStream.to(getFeed(UserUsernameKeyState.class), Produced.with(Serdes.String(), userStateKeyUsernameSerde));

        final KStream<String, UserState> userStateKStream = userCreateEventKStream
                .map((k, v) -> new KeyValue<>(k, UserState.builder()
                        .key(v.getKey())
                        .eventNumber(v.getEventNumber())
                        .username(v.getUserCreateCommand().getUsername())
                        .password(v.getUserCreateCommand().getPassword())
                        .firstName(v.getUserCreateCommand().getFirstName())
                        .lastName(v.getUserCreateCommand().getLastName())
                        .build()));

        userStateKStream.to(getFeed(UserState.class), Produced.with(Serdes.String(), userStateSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
