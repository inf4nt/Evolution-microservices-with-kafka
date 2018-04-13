package com.evolution.direct.message.processor.stream;

import com.evolution.direct.message.processor.event.CreateMessageEvent;
import com.evolution.direct.message.processor.event.MessageStateEvent;
import com.evolution.direct.message.processor.event.UpdateTextMessageEvent;
import com.evolution.direct.message.processor.service.MessageBuilder;
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

import java.util.concurrent.TimeUnit;

@EnableBinding(MessageStateProcessor.class)
public class MessageStateProcessorStream {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener
    @SendTo(MessageStateProcessor.OUTPUT_RESULT)
    public KStream<String, MessageStateEvent> processor(@Input(MessageStateProcessor.INPUT_CREATE) KStream<String, CreateMessageEvent> createStream,
                                                        @Input(MessageStateProcessor.INPUT_UPDATE) KStream<String, UpdateTextMessageEvent> updateStream) {

        Serde<CreateMessageEvent> createSerde = new JsonSerde<>(CreateMessageEvent.class, objectMapper);
        Serde<UpdateTextMessageEvent> updateSerde = new JsonSerde<>(UpdateTextMessageEvent.class, objectMapper);

        return createStream.leftJoin(updateStream, (create, update) -> MessageBuilder.buildState(create, update), JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                Serdes.String(),
                createSerde,
                updateSerde
        );
    }
}
