package com.evolution.direct.message.core.topology;

import com.evolution.core.share.User;
import com.evolution.core.state.MessageDenormalizationState;
import com.evolution.core.state.MessageState;
import com.evolution.core.state.UserState;
import com.evolution.core.temp.MessageStateSenderTemp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.evolution.library.core.BaseService.getFeed;


@Component
public class MessageDenormalizationStateTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageDenormalizationStateTopology(ObjectMapper objectMapper) {
        super(MessageDenormalizationStateTopology.class.getSimpleName(), MessageDenormalizationStateTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {

        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
        Serde<MessageStateSenderTemp> messageStateSenderSerde = new JsonSerde<>(MessageStateSenderTemp.class, objectMapper);
        Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);
        Serde<MessageDenormalizationState> messageDenormalizationStateSerde = new JsonSerde<>(MessageDenormalizationState.class, objectMapper);

        final KTable<String, MessageState> stateMessageKTable = builder.table(getFeed(MessageState.class), Consumed.with(Serdes.String(), messageStateSerde));

        final KTable<String, UserState> stateUserKTable = builder.table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));

        final KStream<String, MessageDenormalizationState> messageDenormalizationStateKStream = stateMessageKTable
                .toStream()
                .selectKey((k, v) -> v.getSender())
                .join(stateUserKTable, (m, us) -> MessageStateSenderTemp.builder()
                                .key(m.getKey())
                                .text(m.getText())
                                .sender(new User(us.getKey(), us.getFirstName(), us.getLastName()))
                                .recipient(m.getRecipient())
                                .build(),
                        Joined.with(Serdes.String(), messageStateSerde, userStateSerde))
                .selectKey((k, v) -> v.getRecipient())
                .join(stateUserKTable, (m, us) -> MessageDenormalizationState.builder()
                                .key(m.getKey())
                                .eventNumber(UUID.randomUUID().toString().replace("-", ""))
                                .text(m.getText())
                                .sender(m.getSender())
                                .recipient(new User(us.getKey(), us.getFirstName(), us.getLastName()))
                                .build(),
                        Joined.with(Serdes.String(), messageStateSenderSerde, userStateSerde));

        messageDenormalizationStateKStream.to(getFeed(MessageDenormalizationState.class), Produced.with(Serdes.String(), messageDenormalizationStateSerde));


        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
