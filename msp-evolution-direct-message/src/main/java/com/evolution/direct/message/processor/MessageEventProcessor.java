package com.evolution.direct.message.processor;

import com.evolution.direct.message.core.MessageEvent;
import com.evolution.direct.message.core.MessageEventHandler;
import com.evolution.direct.message.core.MessageState;
import com.evolution.direct.message.processor.bindings.EventProcessor;
import com.evolution.library.core.v4.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(EventProcessor.class)
public class MessageEventProcessor {

    private final MessageEventHandler messageEventHandler;

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageEventProcessor(MessageEventHandler messageEventHandler,
                                 ObjectMapper objectMapper) {
        this.messageEventHandler = messageEventHandler;
        this.objectMapper = objectMapper;
    }

    @StreamListener(EventProcessor.INPUT)
    @SendTo(EventProcessor.OUTPUT)
    public KStream<String, MessageState> process(KStream<String, MessageEvent> input) {
        final Serde<MessageEvent> messageEventSerde = new JsonSerde<>(MessageEvent.class, objectMapper);
        final Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);

        return input
                .groupByKey(Serialized.with(Serdes.String(), messageEventSerde))
                .aggregate(MessageState::new, (key, event, state) -> messageEventHandler.handle(event, state),
                        Materialized
                                .<String, MessageState, KeyValueStore<Bytes, byte[]>>as(MessageService.getStore(MessageState.class))
                                .withKeySerde(Serdes.String())
                                .withValueSerde(messageStateSerde))
                .toStream();
    }
}
