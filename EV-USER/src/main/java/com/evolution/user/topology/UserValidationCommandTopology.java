package com.evolution.user.topology;

import com.evolution.user.base.core.command.UserCreateCommand;
import com.evolution.user.core.AbstractCommand;
import com.evolution.user.core.AbstractTopology;
import com.evolution.user.topology.core.common.CommandErrors;
import com.evolution.user.topology.core.event.UserCreateEvent;
import com.evolution.user.topology.core.temp.UserUsernameKey;
import com.evolution.user.topology.core.validation.UserCreateCommandValidationResponse;
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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.evolution.user.core.HelpService.getFeed;

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

        Serde<UserCreateEvent> userCreateEventSerde = new JsonSerde<>(UserCreateEvent.class, objectMapper);
        Serde<UserCreateCommandValidationResponse> userCreateCommandValidationResponseSerde = new JsonSerde<>(UserCreateCommandValidationResponse.class, objectMapper);
        Serde<UserCreateCommand> userCreateCommandSerde = new JsonSerde<>(UserCreateCommand.class, objectMapper);
        Serde<UserUsernameKey> userStateKeyUsernameSerde = new JsonSerde<>(UserUsernameKey.class, objectMapper);
        Serde<AbstractCommand> abstractCommandSerde = new JsonSerde<>(AbstractCommand.class, objectMapper);
        Serde<CommandExecute> commandExecuteSerde = new JsonSerde<>(CommandExecute.class, objectMapper);

        final KStream<String, UserCreateCommand> userCreateCommandKStream = builder
                .stream(getFeed(UserCreateCommand.class), Consumed.with(Serdes.String(), userCreateCommandSerde));

        final KTable<String, UserUsernameKey> userStateKeyUsernameKTable = builder
                .table(getFeed(UserUsernameKey.class), Consumed.with(Serdes.String(), userStateKeyUsernameSerde));

        final KStream<String, CommandExecute> commandExecuteKStream =
                userCreateCommandKStream
                        .selectKey((k, v) -> v.getUsername())
                        .leftJoin(userStateKeyUsernameKTable, (uc, us) -> CommandExecute
                                        .builder()
                                        .errors(us == null ? new ArrayList<>() : new ArrayList<String>() {{
                                            add(CommandErrors.USERNAME_NOT_UNIQUE.name());
                                        }})
                                        .key(uc.getKey())
                                        .operationNumber(uc.getOperationNumber())
                                        .build(),
                                Joined.with(Serdes.String(), userCreateCommandSerde, userStateKeyUsernameSerde))
                .map((k, v) -> new KeyValue<>(v.getKey(), v));

        commandExecuteKStream
                .to(getFeed(CommandExecute.class), Produced.with(Serdes.String(), commandExecuteSerde));


        final KStream<String, UserCreateEvent> userCreateEventKStream = commandExecuteKStream
                .filter((k, v) -> v.getErrors().isEmpty())
                .join(userCreateCommandKStream, (ce, uc) -> UserCreateEvent.builder()
                                .key(ce.getKey())
                                .eventNumber(ce.getOperationNumber())
                                .userCreateCommand(uc)
                                .build(),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                        Joined.with(Serdes.String(), commandExecuteSerde, userCreateCommandSerde));

        userCreateEventKStream.to(getFeed(UserCreateEvent.class), Produced.with(Serdes.String(), userCreateEventSerde));

//        final KStream<String, UserCreateCommandValidationResponse> userCreateCommandValidationResponseKStream =
//                userCreateCommandKStream
//                        .selectKey((k, v) -> v.getUsername())
//                        .leftJoin(userStateKeyUsernameKTable, (uc, us) -> UserCreateCommandValidationResponse
//                                        .builder()
//                                        .errors(us == null ? new ArrayList<>() : new ArrayList<CommandErrors>() {{
//                                            add(CommandErrors.USERNAME_NOT_UNIQUE);
//                                        }})
//                                        .key(uc.getKey())
//                                        .operationNumber(uc.getOperationNumber())
//                                        .userCreateCommand(UserCreateCommand.builder()
//                                                .key(uc.getKey())
//                                                .username(uc.getUsername())
//                                                .password(uc.getPassword())
//                                                .firstName(uc.getFirstName())
//                                                .lastName(uc.getLastName())
//                                                .operationNumber(uc.getOperationNumber())
//                                                .build())
//                                        .build(),
//                                Joined.with(Serdes.String(), userCreateCommandSerde, userStateKeyUsernameSerde));
//
//        userCreateCommandValidationResponseKStream
//                .to(getFeed(UserCreateCommandValidationResponse.class), Produced.with(Serdes.String(), userCreateCommandValidationResponseSerde));
//
//        final KStream<String, AbstractCommand> abstractCommandKStream = userCreateCommandValidationResponseKStream
//                .map((k, v) -> new KeyValue<>(k, AbstractCommand.builder()
//                        .key(k)
//                        .operationNumber(v.getOperationNumber())
//                        .errors(v.getErrors())
//                        .build()));
//        abstractCommandKStream.to(getFeed(AbstractCommand.class), Produced.with(Serdes.String(), abstractCommandSerde));


//        final KStream<String, UserCreateEvent> userCreateEventKStream = userCreateCommandValidationResponseKStream
//                .filter((k, v) -> v.getErrors().isEmpty())
//                .map((k, v) -> new KeyValue<>(k, UserCreateEvent.builder()
//                        .key(v.getKey())
//                        .eventNumber(v.getOperationNumber())
//                        .userCreateCommand(v.getUserCreateCommand())
//                        .build()));
//
//        userCreateEventKStream.to(getFeed(UserCreateEvent.class), Produced.with(Serdes.String(), userCreateEventSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
