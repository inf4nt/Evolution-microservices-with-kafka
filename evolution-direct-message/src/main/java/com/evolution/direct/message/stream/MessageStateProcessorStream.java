//package com.evolution.direct.message.stream;
//
//import com.evolution.direct.message.event.*;
//import com.evolution.direct.message.service.MessageEventBuilder;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.kstream.JoinWindows;
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.messaging.handler.annotation.SendTo;
//
//import java.util.concurrent.TimeUnit;
//
//@EnableBinding(MessageStateProcessor.class)
//public class MessageStateProcessorStream {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @StreamListener
//    @SendTo(MessageStateProcessor.OUTPUT_STATE_MESSAGE)
//    public KStream<String, MessageStateEvent> processState(@Input(MessageStateProcessor.INPUT_CREATE_MESSAGE) KStream<String, MessageCreateEvent> createMessageEventKStream,
//                                                           @Input(MessageStateProcessor.INPUT_UPDATE_TEXT_MESSAGE) KStream<String, MessageUpdateTextEvent> updateTextMessageEventKStream,
//                                                           @Input(MessageStateProcessor.INPUT_STATE_USER) KStream<String, UserStateEvent> userStateEventKStream) {
//
//        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
//        Serde<MessageUpdateTextEvent> serdeUpdate = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);
//
//        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);
//
//        Serde<MessageTempStateEvent> serdeTempState = new JsonSerde<>(MessageTempStateEvent.class, objectMapper);
//
//        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);
//
//
//        KStream<String, MessageTempStateEvent> tempState = createMessageEventKStream
//                .join(updateTextMessageEventKStream, (r, l) -> MessageEventBuilder.buildTemp(r, l), JoinWindows.of(TimeUnit.MINUTES.toMillis(500)),
//                        Serdes.String(),
//                        serdeCreate,
//                        serdeUpdate
//                );
//
//
//        // stream for sender
//        KStream<String, MessageStateEvent> tempStateForSender = tempState
//                .selectKey((k, v) -> v.getSender())
//                .join(userStateEventKStream, (r, l) -> MessageEventBuilder.buildStateForSender(r, l), JoinWindows.of(TimeUnit.MINUTES.toMillis(500)),
//                        Serdes.String(),
//                        serdeTempState,
//                        serdeUserState
//                );
//
//        KStream<String, MessageStateEvent> tempStateForRecipient = tempStateForSender;
//
//        // stream for recipient
////        KStream<String, MessageStateEvent> tempStateForRecipient = temp2
////                .join(userStateEventKStream, (r, l) -> MessageEventBuilder.buildStateForRecipient(r, l), JoinWindows.of(TimeUnit.MINUTES.toMillis(500)),
////                        Serdes.String(),
////                        serdeTempState,
////                        serdeUserState
////                );
//
//        return tempStateForSender
//                .join(tempStateForRecipient, (s, r) -> MessageEventBuilder.build(s, r), JoinWindows.of(TimeUnit.MINUTES.toMillis(500)),
//                        Serdes.String(),
//                        serdeMessageState,
//                        serdeMessageState
//                );
//    }
//}
