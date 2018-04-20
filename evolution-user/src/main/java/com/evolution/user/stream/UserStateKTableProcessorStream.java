//package com.evolution.user.stream;
//
//import com.evolution.user.event.UserCreateEvent;
//import com.evolution.user.event.UserStateEvent;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.apache.kafka.streams.kstream.Serialized;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.kafka.support.serializer.JsonSerde;
//
//@EnableBinding(Sink.class)
//public class UserStateKTableProcessorStream {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @StreamListener(Sink.INPUT)
//    public void state(KStream<String, UserCreateEvent> createStream) {
//        Serde<UserCreateEvent> serdeUserEvent = new JsonSerde<>(UserCreateEvent.class, objectMapper);
//        Serde<UserStateEvent> serdeStateUserEvent = new JsonSerde<>(UserStateEvent.class, objectMapper);
//
//        KTable<String, UserStateEvent> kTable =
//                createStream
//                .groupBy((k, v) -> k, Serialized.with(Serdes.String(), serdeUserEvent))
//                .aggregate(() -> new UserStateEvent(), (key, event, state) -> new UserStateEvent(),
//                        serdeStateUserEvent, UserStateEvent.class.getSimpleName() + "Topic"
//                        );
//
//
//        kTable.foreach((k, v) -> System.out.println("key:" + k + ", value:" + v));
//    }
//}
