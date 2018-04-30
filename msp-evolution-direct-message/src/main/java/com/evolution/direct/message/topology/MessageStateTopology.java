//package com.evolution.direct.message.topology;
//
//import com.evolution.direct.message.base.core.command.MessageCreateCommand;
//import com.evolution.direct.message.base.core.command.MessageUpdateTextCommand;
//import com.evolution.direct.message.base.core.state.MessageState;
//import com.evolution.direct.message.core.AbstractTopology;
//import com.evolution.direct.message.topology.core.MessageCreateEvent;
//import com.evolution.direct.message.topology.core.MessageUpdateTextEvent;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.serialization.Serde;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.Consumed;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.KeyValue;
//import org.apache.kafka.streams.StreamsBuilder;
//import org.apache.kafka.streams.kstream.Joined;
//import org.apache.kafka.streams.kstream.KStream;
//import org.apache.kafka.streams.kstream.KTable;
//import org.apache.kafka.streams.kstream.Produced;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.support.serializer.JsonSerde;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//import static com.evolution.direct.message.core.HelpService.getFeed;
//
//
//@Component
//public class MessageStateTopology extends AbstractTopology {
//
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public MessageStateTopology(ObjectMapper objectMapper) {
//        super(MessageStateTopology.class.getSimpleName(), "direct-message-processor", Serdes.String().getClass(), JsonSerde.class);
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void init() {
//        StreamsBuilder builder = new StreamsBuilder();
//
//        Serde<MessageCreateCommand> messageCreateCommandSerde = new JsonSerde<>(MessageCreateCommand.class, objectMapper);
//        Serde<MessageUpdateTextCommand> messageUpdateTextCommandSerde = new JsonSerde<>(MessageUpdateTextCommand.class, objectMapper);
//        Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);
//        Serde<MessageCreateEvent> messageCreateEventSerde = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
//        Serde<MessageUpdateTextEvent> messageUpdateTextEventSerde = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);
//
//        final KStream<String, MessageCreateEvent> messageCreateEventKStream = builder
//                .stream(getFeed(MessageCreateCommand.class), Consumed.with(Serdes.String(), messageCreateCommandSerde))
//                .map((k, v) -> new KeyValue<>(k, MessageCreateEvent.builder()
//                        .key(v.getKey())
//                        .eventNumber(UUID.randomUUID().toString().replace("-", ""))
//                        .sender(v.getSender())
//                        .recipient(v.getRecipient())
//                        .text(v.getText())
//                        .build()));
//
//        messageCreateEventKStream.to(getFeed(MessageCreateEvent.class), Produced.with(Serdes.String(), messageCreateEventSerde));
//
//        final KStream<String, MessageState> messageStateKStream = messageCreateEventKStream
//                .map((k, v) -> new KeyValue<>(k, MessageState.builder()
//                        .key(v.getKey())
//                        .eventNumber(UUID.randomUUID().toString().replace("-", ""))
//                        .text(v.getText())
//                        .sender(v.getSender())
//                        .recipient(v.getRecipient())
//                        .build()));
//
//        messageStateKStream.to(getFeed(MessageState.class), Produced.with(Serdes.String(), messageStateSerde));
//
//        final KStream<String, MessageUpdateTextCommand> messageUpdateTextCommandKStream = builder
//                .stream(getFeed(MessageUpdateTextCommand.class), Consumed.with(Serdes.String(), messageUpdateTextCommandSerde));
//
//        final KStream<String, MessageUpdateTextEvent> messageUpdateTextEventKStream = messageUpdateTextCommandKStream
//                .map((k, v) -> new KeyValue<>(k, MessageUpdateTextEvent.builder()
//                        .key(v.getKey())
//                        .eventNumber(UUID.randomUUID().toString().replace("-", ""))
//                        .text(v.getText())
//                        .build()));
//
//        messageUpdateTextEventKStream.to(getFeed(MessageUpdateTextEvent.class), Produced.with(Serdes.String(), messageUpdateTextEventSerde));
//
//        // update
//        final KTable<String, MessageState> messageStateKTable = builder
//                .table(getFeed(MessageState.class), Consumed.with(Serdes.String(), messageStateSerde));
//
//        final KStream<String, MessageState> stateAfterUpdateKStream = messageUpdateTextEventKStream
//                .join(messageStateKTable, (mu, ms) -> MessageState.builder()
//                                .key(ms.getKey())
//                                .eventNumber(UUID.randomUUID().toString().replace("-", ""))
//                                .text(mu.getText())
//                                .sender(ms.getSender())
//                                .recipient(ms.getRecipient())
//                                .build(),
//                        Joined.with(Serdes.String(), messageUpdateTextEventSerde, messageStateSerde));
//
//        stateAfterUpdateKStream
//                .to(getFeed(MessageState.class), Produced.with(Serdes.String(), messageStateSerde));
//
//        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
//        streams.start();
//    }
//}
