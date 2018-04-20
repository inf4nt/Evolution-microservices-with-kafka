package com.evolution.user.kafka.topology;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserStateEvent;
import com.evolution.user.event.UserUpdateEvent;
import com.evolution.user.kafka.UserEventBuilder;
import com.evolution.user.kafka.core.AbstractTopology;
import com.evolution.user.kafka.core.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static com.evolution.user.kafka.core.EventService.getTopicName;

@Component
public class UserStateTopology extends AbstractTopology {

    @Getter
    private final ObjectMapper objectMapper;

    @Autowired
    public UserStateTopology(ObjectMapper objectMapper) {
        super(Serdes.String().getClass(), JsonSerde.class, true);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserCreateEvent> serdeUserCreate = new JsonSerde<>(UserCreateEvent.class, getObjectMapper());
        Serde<UserUpdateEvent> userUpdateEventSerde = new JsonSerde<>(UserUpdateEvent.class, getObjectMapper());
        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, getObjectMapper());

        final KStream<String, UserCreateEvent> userCreateStream = builder.stream(getTopicName(UserCreateEvent.class), Consumed.with(Serdes.String(), serdeUserCreate));
        final KStream<String, UserUpdateEvent> userUpdateEventKStream = builder.stream(getTopicName(UserUpdateEvent.class), Consumed.with(Serdes.String(), userUpdateEventSerde));

        final KTable<String, UserStateEvent> stateKTable = userCreateStream
                .groupByKey(Serialized.with(Serdes.String(), serdeUserCreate))
                .aggregate(UserStateEvent::new, (key, event, state) -> UserEventBuilder.build(key, event),
//                        Materialized.with(Serdes.String(), serdeUserState).as(EventService.getStoreName(UserStateEvent.class) + "2")
                        Materialized.with(Serdes.String(), serdeUserState)
                );

        stateKTable.toStream().to(getTopicName(UserStateEvent.class), Produced.with(Serdes.String(), serdeUserState));

        userUpdateEventKStream
                .join(stateKTable, (uu, us) -> UserEventBuilder.build(us.getId(), us, uu), Joined.with(Serdes.String(), userUpdateEventSerde, serdeUserState))
                .groupBy((k, v) -> v.getId(), Serialized.with(Serdes.String(), serdeUserState))
//                .aggregate(UserStateEvent::new, (key, event, state) -> UserEventBuilder.build(key, event), Materialized.with(Serdes.String(), serdeUserState).as(EventService.getStoreName(UserStateEvent.class)))
                .aggregate(UserStateEvent::new, (key, event, state) -> UserEventBuilder.build(key, event), Materialized.with(Serdes.String(), serdeUserState))
                .toStream().to(getTopicName(UserStateEvent.class), Produced.with(Serdes.String(), serdeUserState));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
