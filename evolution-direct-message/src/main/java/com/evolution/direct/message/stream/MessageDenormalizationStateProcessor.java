package com.evolution.direct.message.stream;

import com.evolution.direct.message.event.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.concurrent.TimeUnit;

import static com.evolution.direct.message.service.MessageEventBuilder.build;
import static com.evolution.direct.message.service.MessageEventBuilder.buildStateForSender;
import static com.evolution.direct.message.stream.MessageDenormalizationStateKProcessor.INPUT_MESSAGE_CREATE;
import static com.evolution.direct.message.stream.MessageDenormalizationStateKProcessor.INPUT_MESSAGE_UPDATE_TEXT;
import static com.evolution.direct.message.stream.MessageDenormalizationStateKProcessor.INPUT_USER_STATE;

@EnableBinding({MessageDenormalizationStateKProcessor.class})
public class MessageDenormalizationStateProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener
    @SendTo(MessageDenormalizationStateKProcessor.OUTPUT_MESSAGE_DENORMALIZATION_STATE)
    public KStream<String, MessageDenormalizationStateEvent> processDenormalization(@Input(INPUT_USER_STATE) KStream<String, UserStateEvent> userStateEventKStream,
                                                                                    @Input(INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> messageCreateEventKStream,
                                                                                    @Input(INPUT_MESSAGE_UPDATE_TEXT) KStream<String, MessageUpdateTextEvent> messageUpdateTextEvent) {

        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
        Serde<MessageUpdateTextEvent> serdeUpdateText = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);

        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);
        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);
        Serde<MessageDenormalizationStateEvent> serdeMessageDenormalization = new JsonSerde<>(MessageDenormalizationStateEvent.class, objectMapper);

        KStream<String, MessageStateEvent> state = messageCreateEventKStream
                .leftJoin(messageUpdateTextEvent, (c, u) -> build(c, u), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Serdes.String(), serdeCreate, serdeUpdateText);

        return state
                .selectKey((k, v) -> v.getSender())
                .join(userStateEventKStream, (ms, u) -> buildStateForSender(ms, u), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                        Serdes.String(), serdeMessageState, serdeUserState);
    }
}
