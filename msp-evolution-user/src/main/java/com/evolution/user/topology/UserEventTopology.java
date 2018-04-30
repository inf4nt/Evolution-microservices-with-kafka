package com.evolution.user.topology;

import com.evolution.library.core.CommandExecuteStatus;
import com.evolution.user.command.command.UserCreateCommand;
import com.evolution.user.query.state.UserState;
import com.evolution.user.topology.event.UserCreateEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.evolution.library.core.BaseService.getFeed;

@Component
public class UserEventTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserEventTopology(ObjectMapper objectMapper) {
        super(UserEventTopology.class.getSimpleName(), UserEventTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserCreateEvent> userCreateEventSerde = new JsonSerde<>(UserCreateEvent.class, objectMapper);
        Serde<UserCreateCommand> userCreateCommandSerde = new JsonSerde<>(UserCreateCommand.class, objectMapper);
        Serde<CommandExecuteStatus> commandExecuteSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);

        final KStream<String, UserCreateCommand> userCreateCommandKStream = builder
                .stream(getFeed(UserCreateCommand.class), Consumed.with(Serdes.String(), userCreateCommandSerde));

        final KStream<String, CommandExecuteStatus> commandExecuteStatusKStream = builder
                .stream(getFeed(CommandExecuteStatus.class), Consumed.with(Serdes.String(), commandExecuteSerde))
                .filter((k, v) -> v.getErrors().isEmpty());

        final KStream<String, UserCreateEvent> userCreateEventKStream = commandExecuteStatusKStream
                .join(userCreateCommandKStream, (ce, uc) -> UserCreateEvent.builder()
                                .key(ce.getKey())
                                .eventNumber(ce.getOperationNumber())
                                .userCreateCommand(uc)
                                .build(),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                        Joined.with(Serdes.String(), commandExecuteSerde, userCreateCommandSerde));

        userCreateEventKStream.to(getFeed(UserCreateEvent.class), Produced.with(Serdes.String(), userCreateEventSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
