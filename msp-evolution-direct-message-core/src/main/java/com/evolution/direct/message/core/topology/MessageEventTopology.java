package com.evolution.direct.message.core.topology;

import com.evolution.core.command.MessageCreateCommand;
import com.evolution.core.command.MessageUpdateTextCommand;
import com.evolution.core.event.MessageCreateEvent;
import com.evolution.core.event.MessageUpdateTextEvent;
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
        Serde<MessageUpdateTextCommand> messageUpdateTextCommandSerde = new JsonSerde<>(MessageUpdateTextCommand.class, objectMapper);
        Serde<MessageUpdateTextEvent> messageUpdateTextEventSerde = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);

        final KStream<String, CommandExecuteStatus> commandExecuteStatusKStream = builder
                .stream(getFeed(CommandExecuteStatus.class), Consumed.with(Serdes.String(), commandExecuteStatusSerde))
                .filter((k, v) -> v.getErrors().isEmpty());

        final KStream<String, MessageCreateCommand> messageCreateCommandKStream = builder
                .stream(getFeed(MessageCreateCommand.class), Consumed.with(Serdes.String(), messageCreateCommandSerde));

        final KStream<String, MessageCreateEvent> messageCreateEventKStream = messageCreateCommandKStream
                .join(commandExecuteStatusKStream, (mc, ce) -> MessageCreateEvent.builder()
                                .key(mc.getKey())
                                .eventNumber(mc.getOperationNumber())
                                .messageCreateCommand(mc)
                                .build(),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)), Joined.with(Serdes.String(), messageCreateCommandSerde, commandExecuteStatusSerde));

        messageCreateEventKStream.to(getFeed(MessageCreateEvent.class), Produced.with(Serdes.String(), messageCreateEventSerde));

//        // update text
//        final KStream<String, MessageUpdateTextCommand> messageUpdateTextCommandKStream = builder
//                .stream(getFeed(MessageUpdateTextCommand.class), Consumed.with(Serdes.String(), messageUpdateTextCommandSerde));
//
//        final KStream<String, MessageUpdateTextEvent> messageUpdateTextEventKStream = messageUpdateTextCommandKStream
//                .join(commandExecuteStatusKStream, (mu, ce) -> MessageUpdateTextEvent.builder()
//                                .key(mu.getKey())
//                                .eventNumber(mu.getOperationNumber())
//                                .text(mu.getText())
//                                .build(),
//                        JoinWindows.of(TimeUnit.MINUTES.toMillis(5)),
//                        Joined.with(Serdes.String(), messageUpdateTextCommandSerde, commandExecuteStatusSerde));
//
//        messageUpdateTextEventKStream.to(getFeed(MessageUpdateTextEvent.class), Produced.with(Serdes.String(), messageUpdateTextEventSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
        streams.start();
    }
}
