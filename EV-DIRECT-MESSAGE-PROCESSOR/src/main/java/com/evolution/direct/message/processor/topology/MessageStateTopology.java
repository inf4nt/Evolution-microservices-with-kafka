package com.evolution.direct.message.processor.topology;

import com.evolution.core.base.AbstractTopology;
import com.evolution.core.command.MessageCreateCommand;
import com.evolution.core.share.User;
import com.evolution.core.state.MessageState;
import com.evolution.core.temp.MessageStateSender;
import com.evolution.core.state.UserState;
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

import static com.evolution.core.base.HelpService.getFeed;

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

        Serde<MessageCreateCommand> messageCreateCommandSerde = new JsonSerde<>(MessageCreateCommand.class, objectMapper);
        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
        Serde<MessageStateSender> messageStateSenderSerde = new JsonSerde<>(MessageStateSender.class, objectMapper);
        Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);

        final KStream<String, MessageCreateCommand> messageCreateCommandKStream = builder.stream(getFeed(MessageCreateCommand.class), Consumed.with(Serdes.String(), messageCreateCommandSerde));
        final KTable<String, UserState> stateUserKTable = builder.table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));


        final KStream<String, MessageState> messageStateKStream = messageCreateCommandKStream
                .selectKey((k, v) -> v.getSender())
                .join(stateUserKTable, (m, us) -> MessageStateSender.builder()
                                .key(m.getKey())
                                .text(m.getText())
                                .sender(new User(us.getKey(), us.getFirstName(), us.getLastName()))
                                .recipient(m.getRecipient())
                                .build(),
                        Joined.with(Serdes.String(), messageCreateCommandSerde, userStateSerde))
                .selectKey((k, v) -> v.getRecipient())
                .join(stateUserKTable, (m, us) -> MessageState.builder()
                                .key(m.getKey())
                                .eventNumber(UUID.randomUUID().toString().replace("-", ""))
                                .text(m.getText())
                                .sender(m.getSender())
                                .recipient(new User(us.getKey(), us.getFirstName(), us.getLastName()))
                                .build(),
                        Joined.with(Serdes.String(), messageStateSenderSerde, userStateSerde));

        messageStateKStream.to(getFeed(MessageState.class), Produced.with(Serdes.String(), messageStateSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
