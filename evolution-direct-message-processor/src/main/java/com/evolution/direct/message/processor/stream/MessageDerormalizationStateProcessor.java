package com.evolution.direct.message.processor.stream;

import com.evolution.direct.message.processor.event.*;
import com.evolution.direct.message.processor.service.MessageEventBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.evolution.direct.message.processor.stream.MessageDerormalizationStateKProcessor.INPUT_MESSAGE_STATE;
import static com.evolution.direct.message.processor.stream.MessageDerormalizationStateKProcessor.INPUT_USER_STATE;
import static com.evolution.direct.message.processor.stream.MessageDerormalizationStateKProcessor.OUTPUT_MESSAGE_DENORMALIZATION_STATE;

@EnableBinding(MessageDerormalizationStateKProcessor.class)
public class MessageDerormalizationStateProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener
    @SendTo(OUTPUT_MESSAGE_DENORMALIZATION_STATE)
    public KStream<String, MessageDenormalizationStateEvent> processDenormalization(@Input(INPUT_MESSAGE_STATE) KStream<String, MessageStateEvent> messageStateStream,
                                                                                    @Input(INPUT_USER_STATE) KStream<String, UserStateEvent> userStateStream) {

        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);
        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);
        Serde<MessageDenormalizationStateEvent> serdeMessageDenormalization = new JsonSerde<>(MessageDenormalizationStateEvent.class, objectMapper);


        // map key for sender and recipient
        KStream<String, MessageStateEvent> messageStateStreamForSender = messageStateStream
                .map((k, v) -> new KeyValue<>(v.getSender(), MessageEventBuilder.buildForMap(v)));

        KStream<String, MessageStateEvent> messageStateStreamForRecipient = messageStateStream
                .map((k, v) -> new KeyValue<>(v.getRecipient(), MessageEventBuilder.buildForMap(v)));

        // join for sender and recipient
        KStream<String, MessageDenormalizationStateEvent> stateForSender = messageStateStreamForSender
                .join(userStateStream, (ms, u) -> MessageEventBuilder.buildForSender(ms, u), JoinWindows.of(500), Serdes.String(), serdeMessageState, serdeUserState);

        KStream<String, MessageDenormalizationStateEvent> stateForRecipient = messageStateStreamForRecipient
                .join(userStateStream, (mr, u) -> MessageEventBuilder.buildForSender(mr, u), JoinWindows.of(500), Serdes.String(), serdeMessageState, serdeUserState);

        // map state
        KStream<String, MessageDenormalizationStateEvent> stateForSenderRebuildId = stateForSender
                .map((k, v) -> new KeyValue<>(k, MessageEventBuilder.rebuild(v)));

        KStream<String, MessageDenormalizationStateEvent> stateForRecipientRebuildId = stateForRecipient
                .map((k, v) -> new KeyValue<>(k, MessageEventBuilder.rebuild(v)));


        KStream<String, MessageDenormalizationStateEvent> finalState = stateForSenderRebuildId
                .join(stateForRecipientRebuildId, (s, r) -> MessageEventBuilder.build(s, r), JoinWindows.of(500), Serdes.String(), serdeMessageDenormalization, serdeMessageDenormalization);

        return finalState;
    }
}
