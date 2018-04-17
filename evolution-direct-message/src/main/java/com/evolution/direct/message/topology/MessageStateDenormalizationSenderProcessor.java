package com.evolution.direct.message.topology;

import com.evolution.direct.message.event.MessageDenormalizationStateEvent;
import com.evolution.direct.message.event.MessageStateEvent;
import com.evolution.direct.message.event.UserStateEvent;
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
import java.util.concurrent.TimeUnit;

@Component
public class MessageStateDenormalizationSenderProcessor extends AbstractProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    public MessageStateDenormalizationSenderProcessor() {
        super(MessageStateDenormalizationSenderProcessor.class.getSimpleName(), MessageStateDenormalizationSenderProcessor.class.getSimpleName() + "Group");
    }

    @PostConstruct
    @Override
    public void init() {
        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);
        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);

        Serde<MessageDenormalizationStateEvent> serdeMessageDenormalizationState = new JsonSerde<>(MessageDenormalizationStateEvent.class, objectMapper);

        StreamsBuilder builder = new StreamsBuilder();


        KStream<String, MessageStateEvent> stateMessageStream = builder.stream("MessageStateEventTopic", Consumed.with(Serdes.String(), serdeMessageState));

        KStream<String, UserStateEvent> userStateEventStream = builder.stream("UserStateEventTopic", Consumed.with(Serdes.String(), serdeUserState));


        KStream<String, MessageDenormalizationStateEvent> sender = stateMessageStream
                .selectKey((k, v) -> v.getSender())
                .join(userStateEventStream, (ms, u) -> MessageEventBuilder.buildStateForSender(ms, u),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Serdes.String(), serdeMessageState, serdeUserState);

        sender.to(Serdes.String(), serdeMessageDenormalizationState, "MessageDenormalizationStateEventTopic");


        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
