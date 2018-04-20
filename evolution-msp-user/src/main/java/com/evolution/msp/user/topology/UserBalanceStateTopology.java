//package com.evolution.msp.user.topology;
//
//import com.evolution.msp.user.write.event.UserBalanceChangeEvent;
//import com.evolution.msp.user.write.event.UserBalanceState;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.Getter;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.Consumed;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class UserBalanceStateTopology {
//
//    @Autowired
//    @Getter
//    private ObjectMapper objectMapper;
//
//    public StreamsConfig streamsConfig() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(StreamsConfig.CLIENT_ID_CONFIG, "user-msp");
//
////        if (isRandomGroupId) {
////            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
////        } else {
////            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
////        }
//
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "UserStateTopology");
//        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        return new StreamsConfig(props);
//    }
//
//    @PostConstruct
//    public void init() {
//        StreamsBuilder builder = new StreamsBuilder();
//
//        Serde<UserBalanceChangeEvent> userBalanceChangeEventSerde = new JsonSerde<>(UserBalanceChangeEvent.class, getObjectMapper());
//        Serde<UserBalanceState> userBalanceStateSerde = new JsonSerde<>(UserBalanceState.class, getObjectMapper());
//
//        final KStream<String, UserBalanceChangeEvent> userBalanceChangeEventKStream = builder
//                .stream("UserBalanceChangeEventFeed", Consumed.with(Serdes.String(), userBalanceChangeEventSerde));
//
//        final KTable<String, UserBalanceState> stateKTable = userBalanceChangeEventKStream
//                .groupBy((k, v) -> v.getUsername(), Serialized.with(Serdes.String(), userBalanceChangeEventSerde))
//                .aggregate(UserBalanceState::new, (key, event, state) -> UserBalanceState.builder()
//                        .key(key)
//                        .username(event.getUsername())
//                        .balance(state != null && state.getBalance() != null ? event.getBalance() + state.getBalance() : event.getBalance())
//                        .build(), Materialized.with(Serdes.String(), userBalanceStateSerde));
//
//        stateKTable.toStream()
//                .to("UserBalanceStateFeed", Produced.with(Serdes.String(), userBalanceStateSerde));
//
//        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
//        streams.start();
//    }
//}
