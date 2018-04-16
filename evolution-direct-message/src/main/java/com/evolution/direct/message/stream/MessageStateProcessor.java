//package com.evolution.direct.message.stream;
//
//import com.evolution.direct.message.event.MessageCreateEvent;
//import com.evolution.direct.message.event.MessageStateEvent;
//import com.evolution.direct.message.event.MessageUpdateTextEvent;
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
//import static com.evolution.direct.message.service.MessageEventBuilder.build;
//
//@EnableBinding(MessageStateKafkaProcessor.class)
//public class MessageStateProcessor {
//
//    private final static Logger logger = LoggerFactory.getLogger(MessageStateProcessor.class);
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @StreamListener
//    @SendTo(MessageStateKafkaProcessor.OUTPUT_MESSAGE_STATE)
//    public KStream<String, MessageStateEvent> processState(@Input(MessageStateKafkaProcessor.INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> messageCreateStream,
//                                                           @Input(MessageStateKafkaProcessor.INPUT_MESSAGE_UPDATE_TEXT) KStream<String, MessageUpdateTextEvent> messageUpdateTextEvent) {
//
//        logger.info("Catch streams: " + messageCreateStream + ", " + messageUpdateTextEvent);
//        logger.info("Start create state stream");
//
//        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
//        Serde<MessageUpdateTextEvent> serdeUpdateText = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);
//
//        return messageCreateStream
//                .leftJoin(messageUpdateTextEvent, (c, u) -> build(c, u), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Serdes.String(), serdeCreate, serdeUpdateText);
//    }
//}
