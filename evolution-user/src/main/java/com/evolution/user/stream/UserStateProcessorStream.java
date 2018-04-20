//package com.evolution.user.stream;
//
//import com.evolution.user.event.UserCreateEvent;
//import com.evolution.user.event.UserStateEvent;
//import com.evolution.user.event.UserUpdateEvent;
//import com.evolution.user.service.UserEventBuilder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.kstream.JoinWindows;
//import org.apache.kafka.streams.kstream.KStream;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.messaging.handler.annotation.SendTo;
//
//import java.util.concurrent.TimeUnit;
//
//@EnableBinding(UserStateProcessor.class)
//public class UserStateProcessorStream {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserStateProcessorStream.class);
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @StreamListener
//    @SendTo(UserStateProcessor.OUTPUT_STATE_USER)
//    public KStream<String, UserStateEvent> generateStateKStream(@Input(UserStateProcessor.INPUT_CREATE_USER) KStream<String, UserCreateEvent> createStream,
//                                                                @Input(UserStateProcessor.INPUT_UPDATE_USER) KStream<String, UserUpdateEvent> updateStream) {
//
//        logger.info("Catch streams: " + createStream + ", " + updateStream);
//        logger.info("Start create state stream");
//
//        Serde<UserCreateEvent> serdeCreate = new JsonSerde<>(UserCreateEvent.class, objectMapper);
//        Serde<UserUpdateEvent> serdeUpdate = new JsonSerde<>(UserUpdateEvent.class, objectMapper);
//
//        createStream.foreach((k, v) -> System.out.println("stream key:" + k + ", value:" + v));
//
//        return createStream.leftJoin(updateStream, (create, update) -> UserEventBuilder.buildState(create, update), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
//                Serdes.String(),
//                serdeCreate,
//                serdeUpdate
//        );
//    }
//}
