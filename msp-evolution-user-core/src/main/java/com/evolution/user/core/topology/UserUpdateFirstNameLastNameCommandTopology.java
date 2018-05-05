package com.evolution.user.core.topology;

import com.evolution.core.command.UserUpdateFirstNameLastNameCommand;
import com.evolution.core.common.CommandExecuteStatusEnum;
import com.evolution.core.state.UserState;
import com.evolution.library.core.CommandExecuteStatus;
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

import java.util.ArrayList;

import static com.evolution.core.common.CommandExecuteStatusEnum.USER_NOT_FOUND;
import static com.evolution.library.core.BaseService.getFeed;

@Component
public class UserUpdateFirstNameLastNameCommandTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserUpdateFirstNameLastNameCommandTopology(ObjectMapper objectMapper) {
        super(UserUpdateFirstNameLastNameCommandTopology.class.getSimpleName(),
                UserUpdateFirstNameLastNameCommandTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<UserUpdateFirstNameLastNameCommand> userUpdateFirstNameLastNameCommandSerde = new JsonSerde<>(UserUpdateFirstNameLastNameCommand.class, objectMapper);
        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
        Serde<CommandExecuteStatus> commandExecuteStatusSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);

        final KStream<String, UserUpdateFirstNameLastNameCommand> userUpdateFirstNameLastNameCommandKStream =
                builder.stream(getFeed(UserUpdateFirstNameLastNameCommand.class), Consumed.with(Serdes.String(), userUpdateFirstNameLastNameCommandSerde));

        final KTable<String, UserState> userStateKTable = builder.table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));

        final KStream<String, CommandExecuteStatus> executeStatusKStream = userUpdateFirstNameLastNameCommandKStream
                .leftJoin(userStateKTable, (c, us) -> CommandExecuteStatus.builder()
                        .key(c.getKey())
                        .errors(us == null ? new ArrayList<String>() {{
                            add(USER_NOT_FOUND.name());
                        }} : new ArrayList<>())
                        .operationNumber(c.getOperationNumber())
                        .build(), Joined.with(Serdes.String(), userUpdateFirstNameLastNameCommandSerde, userStateSerde));

        executeStatusKStream.to(getFeed(CommandExecuteStatus.class), Produced.with(Serdes.String(), commandExecuteStatusSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
