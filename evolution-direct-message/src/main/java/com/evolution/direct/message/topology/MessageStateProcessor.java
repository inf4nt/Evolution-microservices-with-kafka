package com.evolution.direct.message.topology;

import com.evolution.direct.message.event.MessageCreateEvent;
import com.evolution.direct.message.event.MessageStateEvent;
import com.evolution.direct.message.event.MessageUpdateTextEvent;
import com.evolution.direct.message.service.MessageEventBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageStateProcessor extends AbstractProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    public MessageStateProcessor() {
        super(MessageStateProcessor.class.getSimpleName(), MessageStateProcessor.class.getSimpleName() + "Group");
    }

    @PostConstruct
    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();
        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
        Serde<MessageUpdateTextEvent> serdeUpdateText = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);
        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);

        KStream<String, MessageCreateEvent> createStream = builder.stream("MessageCreateEventTopic", Consumed.with(Serdes.String(), serdeCreate));
        KStream<String, MessageUpdateTextEvent> updateTextStream = builder.stream("MessageUpdateTextEventTopic", Consumed.with(Serdes.String(), serdeUpdateText));

        createStream.leftJoin(updateTextStream, (c, u) -> MessageEventBuilder.build(c, u), JoinWindows.of(500))
                .to("MessageStateEventTopic");

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
