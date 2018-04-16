package com.evolution.direct.message.processor.stream;

import com.evolution.direct.message.processor.event.MessageCreateEvent;
import com.evolution.direct.message.processor.event.MessageStateEvent;
import com.evolution.direct.message.processor.event.MessageUpdateTextEvent;
import com.evolution.direct.message.processor.service.MessageEventBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;
import static com.evolution.direct.message.processor.stream.MessageStateKProcessor.INPUT_MESSAGE_CREATE;
import static com.evolution.direct.message.processor.stream.MessageStateKProcessor.INPUT_MESSAGE_UPDATE_TEXT;
import static com.evolution.direct.message.processor.stream.MessageStateKProcessor.OUTPUT_MESSAGE_STATE;

@EnableBinding(MessageStateKProcessor.class)
public class MessageStateProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener
    @SendTo(OUTPUT_MESSAGE_STATE)
    public KStream<String, MessageStateEvent> processState(@Input(INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> createStream,
                                                           @Input(INPUT_MESSAGE_UPDATE_TEXT) KStream<String, MessageUpdateTextEvent> updateTextStream) {

        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
        Serde<MessageUpdateTextEvent> serdeUpdateText = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);

        return createStream.leftJoin(updateTextStream, (c, u) -> MessageEventBuilder.buildState(c, u), JoinWindows.of(999),
                Serdes.String(),
                serdeCreate,
                serdeUpdateText
        );
    }
}
