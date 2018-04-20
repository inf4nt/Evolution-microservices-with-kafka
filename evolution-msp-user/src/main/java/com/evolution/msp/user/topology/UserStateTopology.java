package com.evolution.msp.user.topology;

import com.evolution.msp.user.write.command.UserCreateCommand;
import com.evolution.msp.user.write.command.UserUpdateUsernameCommand;
import com.evolution.msp.user.write.event.UserBalanceChangeEvent;
import com.evolution.msp.user.write.event.UserBalanceState;
import com.evolution.msp.user.write.service.UserState;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class UserStateTopology {

    @Autowired
    @Getter
    private ObjectMapper objectMapper;

    public StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "user-msp");

//        if (isRandomGroupId) {
//            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
//        } else {
//            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        }

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "UserStateTopology");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new StreamsConfig(props);
    }

    @PostConstruct
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserCreateCommand> userCreateCommandSerde = new JsonSerde<>(UserCreateCommand.class, getObjectMapper());
        Serde<UserUpdateUsernameCommand> updateUsernameCommandSerde = new JsonSerde<>(UserUpdateUsernameCommand.class, getObjectMapper());
        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, getObjectMapper());

        builder.stream("UserCreateCommandFeed", Consumed.with(Serdes.String(), userCreateCommandSerde))
                .map((k, v) -> new KeyValue<>(k, UserState.builder()
                        .key(v.getKey())
                        .firstName(v.getFirstName())
                        .lastName(v.getLastName())
                        .password(v.getPassword())
                        .username(v.getUsername())
                        .build()))
                .to("UserStateFeed", Produced.with(Serdes.String(), userStateSerde));


        final KStream<String, UserUpdateUsernameCommand> userUpdateUsernameCommandKStream = builder
                .stream("UserUpdateUsernameCommandFeed", Consumed.with(Serdes.String(), updateUsernameCommandSerde));

        final KTable<String, UserState> stateKTableCreate = builder.table("UserStateFeed", Consumed.with(Serdes.String(), userStateSerde));

        userUpdateUsernameCommandKStream.join(stateKTableCreate, (r, l) -> UserState.builder()
                .key(l.getKey())
                .username(r.getUsername())
                .firstName(l.getFirstName())
                .lastName(l.getLastName())
                .password(l.getPassword())
                .build(), Joined.with(Serdes.String(), updateUsernameCommandSerde, userStateSerde))
                .to("UserStateFeed", Produced.with(Serdes.String(), userStateSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
