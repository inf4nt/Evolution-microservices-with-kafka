//package com.evolution.user.core.topology;
//
//import com.evolution.core.command.UserUpdateFirstNameLastNameCommand;
//import com.evolution.core.event.UserUpdateFirstNameLastNameEvent;
//import com.evolution.core.state.UserState;
//import com.evolution.library.core.CommandExecuteStatus;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.Consumed;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//import static com.evolution.library.core.BaseService.getFeed;
//
//@Component
//public class UserUpdateFirstNameLastNameEventTopology extends AbstractTopology {
//
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public UserUpdateFirstNameLastNameEventTopology(ObjectMapper objectMapper) {
//        super(UserUpdateFirstNameLastNameEventTopology.class.getSimpleName(),
//                UserUpdateFirstNameLastNameEventTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void init() {
//        StreamsBuilder builder = new StreamsBuilder();
//
//        Serde<UserUpdateFirstNameLastNameCommand> userUpdateFirstNameLastNameCommandSerde = new JsonSerde<>(UserUpdateFirstNameLastNameCommand.class, objectMapper);
//        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
//        Serde<CommandExecuteStatus> commandExecuteStatusSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);
//        Serde<UserUpdateFirstNameLastNameEvent> userUpdateFirstNameLastNameEventSerde = new JsonSerde<>(UserUpdateFirstNameLastNameEvent.class, objectMapper);
//
//        final KStream<String, UserUpdateFirstNameLastNameCommand> userUpdateFirstNameLastNameCommandKStream =
//                builder.stream(getFeed(UserUpdateFirstNameLastNameCommand.class), Consumed.with(Serdes.String(), userUpdateFirstNameLastNameCommandSerde));
//
//        final KStream<String, CommandExecuteStatus> executeStatusKStream = builder
//                .stream(getFeed(CommandExecuteStatus.class), Consumed.with(Serdes.String(), commandExecuteStatusSerde))
//                .filter((k, v) -> v.getErrors().isEmpty());
//
//        final KTable<String, UserState> userStateKTable = builder
//                .table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));
//
//        final KStream<String, UserUpdateFirstNameLastNameEvent> userUpdateFirstNameLastNameEventKStream =
//                userUpdateFirstNameLastNameCommandKStream
//                        .join(executeStatusKStream, (c, e) -> UserUpdateFirstNameLastNameEvent.builder()
//                                        .key(c.getKey())
//                                        .eventNumber(c.getOperationNumber())
//                                        .command(c)
//                                        .build(),
//                                JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Joined.with(Serdes.String(), userUpdateFirstNameLastNameCommandSerde, commandExecuteStatusSerde));
//
//        userUpdateFirstNameLastNameEventKStream.to(getFeed(UserUpdateFirstNameLastNameEvent.class), Produced.with(Serdes.String(), userUpdateFirstNameLastNameEventSerde));
//
//        final KStream<String, UserState> state = userUpdateFirstNameLastNameEventKStream
//                .join(userStateKTable, (u, us) ->
//                        UserState.builder()
//                                .key(us.getKey())
//                                .username(us.getUsername())
//                                .password(us.getPassword())
//                                .firstName(u.getCommand().getFirstName())
//                                .lastName(u.getCommand().getLastName())
//                                .eventNumber(u.getEventNumber())
//                                .build()
//                        , Joined.with(Serdes.String(), userUpdateFirstNameLastNameEventSerde, userStateSerde));
//
//        state.to(getFeed(UserState.class), Produced.with(Serdes.String(), userStateSerde));
//
//        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
//        streams.start();
//    }
//}
