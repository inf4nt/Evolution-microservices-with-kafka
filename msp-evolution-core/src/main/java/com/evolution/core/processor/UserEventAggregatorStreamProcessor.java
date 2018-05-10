package com.evolution.core.processor;

import com.evolution.core.aggregator.UserEventAggregator;
import com.evolution.core.processor.bindings.UserEventAggregatorProcessor;
import com.evolution.core.state.UserState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.evolution.core.processor.bindings.UserEventAggregatorProcessor.INPUT;
import static com.evolution.core.processor.bindings.UserEventAggregatorProcessor.OUTPUT;

@EnableBinding(UserEventAggregatorProcessor.class)
public class UserEventAggregatorStreamProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(INPUT)
    @SendTo(OUTPUT)
    public KStream<String, UserState> aggregator(KStream<String, UserEventAggregator> input) {
        System.out.println("UserEventAggregatorStreamProcessor");
        final Serde<UserEventAggregator> aggregatorSerde = new JsonSerde<>(UserEventAggregator.class, objectMapper);
        final Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);

//        input.foreach((k, v) -> System.out.println("UserEventAggregator key:" + k + ", value:" + v));

        //todo create event handler ! for parse event type
        final KTable<String, UserState> userStateKTable = input
                .groupByKey(Serialized.with(Serdes.String(), aggregatorSerde))
                .aggregate(UserState::new, (key, event, state) -> UserState.builder()
                        .key(event.getKey())
                        .eventNumber(event.getCommandNumber())
                        .username(event.getUsername())
                        .password(event.getPassword())
                        .firstName(event.getFirstName())
                        .lastName(event.getLastName())
                        .build(), Materialized.with(Serdes.String(), userStateSerde));

        return userStateKTable.toStream();
    }
}
