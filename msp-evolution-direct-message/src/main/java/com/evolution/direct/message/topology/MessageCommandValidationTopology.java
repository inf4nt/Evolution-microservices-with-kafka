package com.evolution.direct.message.topology;

import com.evolution.direct.message.command.command.MessageCreateCommand;
import com.evolution.direct.message.topology.common.CommandExecuteStatusEnum;
import com.evolution.direct.message.topology.state.UserState;
import com.evolution.library.core.CommandExecuteStatus;
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

import static com.evolution.library.core.BaseService.getFeed;

@Component
public class MessageCommandValidationTopology extends AbstractTopology {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageCommandValidationTopology(ObjectMapper objectMapper) {
        super(MessageCommandValidationTopology.class.getSimpleName(), MessageCommandValidationTopology.class.getSimpleName(), Serdes.String().getClass(), JsonSerde.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public void init() {
        StreamsBuilder builder = new StreamsBuilder();

        Serde<MessageCreateCommand> messageCreateCommandSerde = new JsonSerde<>(MessageCreateCommand.class, objectMapper);
        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
        Serde<CommandExecuteStatus> commandExecuteStatusSerde = new JsonSerde<>(CommandExecuteStatus.class, objectMapper);

        final KStream<String, MessageCreateCommand> messageCreateCommandKStream = builder
                .stream(getFeed(MessageCreateCommand.class), Consumed.with(Serdes.String(), messageCreateCommandSerde));

        final KTable<String, UserState> userStateKTable = builder
                .table(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));

        final KStream<String, CommandExecuteStatus> commandExecuteStatusSenderKStream = messageCreateCommandKStream
                .selectKey((k, v) -> v.getSender())
                .leftJoin(userStateKTable, (mcc, us) -> CommandExecuteStatus.builder()
                        .key(mcc.getKey())
                        .operationNumber(mcc.getOperationNumber())
                        .errors(us == null ? new ArrayList<String>() {{
                            add(CommandExecuteStatusEnum.MESSAGE_SENDER_NOT_FOUND.name());
                        }} : new ArrayList<>())
                        .build(), Joined.with(Serdes.String(), messageCreateCommandSerde, userStateSerde))
                .map((k, v) -> new KeyValue<>(v.getKey(), v));

        final KStream<String, CommandExecuteStatus> commandExecuteStatusRecipientKStream = messageCreateCommandKStream
                .selectKey((k, v) -> v.getRecipient())
                .leftJoin(userStateKTable, (mcc, us) -> CommandExecuteStatus.builder()
                        .key(mcc.getKey())
                        .operationNumber(mcc.getOperationNumber())
                        .errors(us == null ? new ArrayList<String>() {{
                            add(CommandExecuteStatusEnum.MESSAGE_RECIPIENT_NOT_FOUND.name());
                        }} : new ArrayList<>())
                        .build(), Joined.with(Serdes.String(), messageCreateCommandSerde, userStateSerde))
                .map((k, v) -> new KeyValue<>(v.getKey(), v));

        final KStream<String, CommandExecuteStatus> commandExecuteStatusKStream =
                commandExecuteStatusSenderKStream
                        .join(commandExecuteStatusRecipientKStream, (s, r) -> CommandExecuteStatus.builder()
                                        .key(s.getKey())
                                        .operationNumber(s.getOperationNumber())
                                        .errors(new ArrayList<String>() {{
                                            addAll(s.getErrors());
                                            addAll(r.getErrors());
                                        }})
                                        .build(),
                                JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
                                Joined.with(Serdes.String(), commandExecuteStatusSerde, commandExecuteStatusSerde));

        commandExecuteStatusKStream.to(getFeed(CommandExecuteStatus.class), Produced.with(Serdes.String(), commandExecuteStatusSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
