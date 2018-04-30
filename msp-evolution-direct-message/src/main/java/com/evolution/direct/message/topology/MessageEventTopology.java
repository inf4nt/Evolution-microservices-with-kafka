package com.evolution.direct.message.topology;

import com.evolution.direct.message.command.command.MessageCreateCommand;
import com.evolution.direct.message.topology.event.MessageCreateEvent;
import com.evolution.direct.message.topology.state.UserState;
import com.evolution.library.core.CommandExecuteStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.evolution.library.core.BaseService.getFeed;

@Component
public class MessageEventTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageEventTopology(ObjectMapper objectMapper) {
        super(MessageEventTopology.class.getSimpleName(), MessageEventTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<MessageCreateCommand> messageCreateCommandSerde = new JsonSerde<>(MessageCreateCommand.class, objectMapper);
        Serde<CommandExecuteStatus> commandExecuteStatusSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);
        Serde<MessageCreateEvent> messageCreateEventSerde = new JsonSerde<>(MessageCreateEvent.class, objectMapper);

        final KStream<String, MessageCreateCommand> messageCreateCommandKStream = builder
                .stream(getFeed(MessageCreateCommand.class), Consumed.with(Serdes.String(), messageCreateCommandSerde));

        final KStream<String, CommandExecuteStatus> commandExecuteStatusKStream = builder
                .stream(getFeed(CommandExecuteStatus.class), Consumed.with(Serdes.String(), commandExecuteStatusSerde))
                .filter((k, v) -> v.getErrors().isEmpty());

        final KStream<String, MessageCreateEvent> messageCreateEventKStream = messageCreateCommandKStream
                .join(commandExecuteStatusKStream, (mc, ce) -> MessageCreateEvent.builder()
                                .key(mc.getKey())
                                .eventNumber(mc.getOperationNumber())
                                .messageCreateCommand(mc)
                                .build(),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Joined.with(Serdes.String(), messageCreateCommandSerde, commandExecuteStatusSerde));

        messageCreateEventKStream.to(getFeed(MessageCreateEvent.class), Produced.with(Serdes.String(), messageCreateEventSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
