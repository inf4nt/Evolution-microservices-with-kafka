package com.evolution.user.topology;


import com.evolution.user.base.core.command.UserCreateCommand;
import com.evolution.user.base.core.command.UserUpdateUsernameCommand;
import com.evolution.user.base.core.state.UserState;
import com.evolution.user.core.AbstractTopology;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.evolution.user.core.HelpService.getFeed;


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

        Serde<UserCreateCommand> userCreateCommandSerde = new JsonSerde<>(UserCreateCommand.class, objectMapper);
        Serde<UserUpdateUsernameCommand> updateUsernameCommandSerde = new JsonSerde<>(UserUpdateUsernameCommand.class, objectMapper);
        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);

        builder.stream(getFeed(UserCreateCommand.class), Consumed.with(Serdes.String(), userCreateCommandSerde))
                .map((k, v) -> new KeyValue<>(k, UserState.builder()
                        .key(v.getKey())
                        .firstName(v.getFirstName())
                        .eventNumber(UUID.randomUUID().toString().replace("-", ""))
                        .lastName(v.getLastName())
                        .password(v.getPassword())
                        .username(v.getUsername())
                        .build()))
                .to(getFeed(UserState.class), Produced.with(Serdes.String(), userStateSerde));


        final KStream<String, UserUpdateUsernameCommand> userUpdateUsernameCommandKStream = builder
                .stream(getFeed(UserUpdateUsernameCommand.class), Consumed.with(Serdes.String(), updateUsernameCommandSerde));

        final KTable<String, UserState> stateUserKTable = builder.table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));

        userUpdateUsernameCommandKStream.join(stateUserKTable, (event, state) -> UserState.builder()
                .key(state.getKey())
                .username(event.getUsername())
                .eventNumber(UUID.randomUUID().toString().replace("-", ""))
                .firstName(state.getFirstName())
                .lastName(state.getLastName())
                .password(state.getPassword())
                .build(), Joined.with(Serdes.String(), updateUsernameCommandSerde, userStateSerde))
                .to(getFeed(UserState.class), Produced.with(Serdes.String(), userStateSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
