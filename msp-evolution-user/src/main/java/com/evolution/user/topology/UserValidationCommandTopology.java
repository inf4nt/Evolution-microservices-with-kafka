package com.evolution.user.topology;

import com.evolution.library.core.CommandExecuteStatus;
import com.evolution.user.command.command.UserCreateCommand;
import com.evolution.user.topology.common.CommandExecuteStatusEnum;
import com.evolution.user.topology.state.UserUsernameKeyState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.evolution.library.core.BaseService.getFeed;

@Component
public class UserValidationCommandTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserValidationCommandTopology(ObjectMapper objectMapper) {
        super(UserValidationCommandTopology.class.getSimpleName(), UserValidationCommandTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserCreateCommand> userCreateCommandSerde = new JsonSerde<>(UserCreateCommand.class, objectMapper);
        Serde<UserUsernameKeyState> userStateKeyUsernameSerde = new JsonSerde<>(UserUsernameKeyState.class, objectMapper);
        Serde<CommandExecuteStatus> commandExecuteSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);

        final KStream<String, UserCreateCommand> userCreateCommandKStream = builder
                .stream(getFeed(UserCreateCommand.class), Consumed.with(Serdes.String(), userCreateCommandSerde));

        final KTable<String, UserUsernameKeyState> userStateKeyUsernameKTable = builder
                .table(getFeed(UserUsernameKeyState.class), Consumed.with(Serdes.String(), userStateKeyUsernameSerde));

        final KStream<String, CommandExecuteStatus> commandExecuteKStream =
                userCreateCommandKStream
                .selectKey((k, v) -> v.getUsername())
                .leftJoin(userStateKeyUsernameKTable, (uc, us) -> CommandExecuteStatus
                                .builder()
                                .errors(us == null ? new ArrayList<>() : new ArrayList<String>() {{
                                    add(CommandExecuteStatusEnum.USERNAME_NOT_UNIQUE.name());
                                }})
                                .key(uc.getKey())
                                .operationNumber(uc.getOperationNumber())
                                .build(),
                        Joined.with(Serdes.String(), userCreateCommandSerde, userStateKeyUsernameSerde))
                .map((k, v) -> new KeyValue<>(v.getKey(), v));

        commandExecuteKStream
                .to(getFeed(CommandExecuteStatus.class), Produced.with(Serdes.String(), commandExecuteSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
