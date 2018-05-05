package com.evolution.direct.message.core.topology;

import com.evolution.core.event.MessageCreateEvent;
import com.evolution.core.event.MessageUpdateTextEvent;
import com.evolution.core.state.MessageState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import static com.evolution.library.core.BaseService.getFeed;


@Component
public class MessageStateTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageStateTopology(ObjectMapper objectMapper) {
        super(MessageStateTopology.class.getSimpleName(), "direct-message-processor", Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);
        Serde<MessageCreateEvent> messageCreateEventSerde = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
        Serde<MessageUpdateTextEvent> messageUpdateTextEventSerde = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);

        final KStream<String, MessageCreateEvent> messageCreateEventKStream = builder
                .stream(getFeed(MessageCreateEvent.class), Consumed.with(Serdes.String(), messageCreateEventSerde));

        final KStream<String, MessageState> messageStateKStream = messageCreateEventKStream
                .map((k, v) -> new KeyValue<>(k, MessageState.builder()
                        .key(v.getKey())
                        .eventNumber(v.getEventNumber())
                        .text(v.getMessageCreateCommand().getText())
                        .sender(v.getMessageCreateCommand().getSender())
                        .recipient(v.getMessageCreateCommand().getRecipient())
                        .build()));

        messageStateKStream.to(getFeed(MessageState.class), Produced.with(Serdes.String(), messageStateSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
