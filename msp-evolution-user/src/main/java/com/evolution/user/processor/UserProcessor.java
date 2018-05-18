//package com.evolution.user.processor;
//
//import com.evolution.library.core.v4.MessageService;
//import com.evolution.user.core.*;
//import com.evolution.user.core.common.UserEventStatus;
//import com.evolution.user.processor.bindings.CommandProcessor;
//import com.evolution.user.processor.bindings.EventProcessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.common.utils.Bytes;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.Materialized;
//import org.apache.kafka.streams.kstream.Serialized;
//import org.apache.kafka.streams.state.KeyValueStore;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.support.MessageBuilder;
//
//import javax.validation.Valid;
//
//@EnableBinding({CommandProcessor.class, EventProcessor.class})
//public class UserProcessor {
//
//    private final UserCommandHandler userCommandHandler;
//
//    private final UserEventHandler userEventHandler;
//
//    private final ObjectMapper objectMapper;
//
//    public UserProcessor(UserCommandHandler userCommandHandler, UserEventHandler userEventHandler, ObjectMapper objectMapper) {
//        this.userCommandHandler = userCommandHandler;
//        this.userEventHandler = userEventHandler;
//        this.objectMapper = objectMapper;
//    }
//
//    @StreamListener(CommandProcessor.INPUT)
//    @SendTo(CommandProcessor.OUTPUT)
//    public Message<UserEvent> process(@Valid UserCommand command) {
//        return MessageBuilder
//                .withPayload(userCommandHandler.handle(command))
//                .setHeader(KafkaHeaders.MESSAGE_KEY, command.getKey().getBytes())
//                .build();
//    }
//
//    @StreamListener(EventProcessor.INPUT)
//    @SendTo(EventProcessor.OUTPUT)
//    public KStream<String, UserState> process(@Valid KStream<String, UserEvent> input) {
//        System.out.println("UserEventStreamProcessor");
//        final Serde<UserState> userStateEventSerde = new JsonSerde<>(UserState.class, objectMapper);
//        final Serde<UserEvent> userEventSerde = new JsonSerde<>(UserEvent.class, objectMapper);
//
//        return input
//                .filter((k, v) -> v.getEventStatus() == UserEventStatus.PROGRESS)
//                .groupByKey(Serialized.with(Serdes.String(), userEventSerde))
//                .aggregate(UserState::new,
//                        (key, event, state) -> userEventHandler.handle(event, state),
//                        Materialized
//                                .<String, UserState, KeyValueStore<Bytes, byte[]>>as(MessageService.getStore(UserState.class))
//                                .withKeySerde(Serdes.String())
//                                .withValueSerde(userStateEventSerde))
//                .toStream();
//    }
//}
