//package com.evolution.direct.message.topology;
//
//import com.evolution.direct.message.base.core.share.User;
//import com.evolution.direct.message.base.core.state.MessageDenormalizationState;
//import com.evolution.direct.message.base.core.state.MessageState;
//import com.evolution.direct.message.core.AbstractTopology;
//import com.evolution.direct.message.topology.core.MessageStateSenderTemp;
//import com.evolution.direct.message.topology.core.UserState;
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
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import static com.evolution.direct.message.core.HelpService.getFeed;
//
//@Component
//public class MessageDenormalizationStateAfterUpdateUserStateTopology extends AbstractTopology {
//
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public MessageDenormalizationStateAfterUpdateUserStateTopology(ObjectMapper objectMapper) {
//        super(MessageDenormalizationStateAfterUpdateUserStateTopology.class.getSimpleName(),
//                MessageDenormalizationStateAfterUpdateUserStateTopology.class.getSimpleName(),
//                Serdes.String().getClass(), JsonSerde.class);
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public void init() {
//        StreamsBuilder builder = new StreamsBuilder();
//
//        Serde<UserState> userStateSerde = new JsonSerde<>(UserState.class, objectMapper);
//        Serde<MessageStateSenderTemp> messageStateSenderSerde = new JsonSerde<>(MessageStateSenderTemp.class, objectMapper);
//        Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);
//        Serde<MessageDenormalizationState> messageDenormalizationStateSerde = new JsonSerde<>(MessageDenormalizationState.class, objectMapper);
//
//        final KStream<String, UserState> userStateKStream = builder.stream(getFeed(UserState.class), Consumed.with(Serdes.String(), userStateSerde));
//        final KTable<String, MessageDenormalizationState> messageDenormalizationStateKTable = builder
//                .table(getFeed(MessageDenormalizationState.class), Consumed.with(Serdes.String(), messageDenormalizationStateSerde));
//
//
//        messageDenormalizationStateKTable
//                .toStream()
//                .selectKey((k, v) -> v.getSender())
//                .join(userStateKStream, (m, us) -> MessageStateSenderTemp.builder()
//                                .key(m.getKey())
//                                .text(m.getText())
//                                .sender(new User(us.getKey(), us.getFirstName(), us.getLastName()))
//                                .recipient(m.getRecipient())
//                                .build(),
//                        JoinWindows.of(TimeUnit.MINUTES.toMillis(1)), Joined.with(Serdes.String(), messageDenormalizationStateSerde, userStateSerde))
//                .selectKey((k, v) -> v.getRecipient())
//                .join(userStateKStream, (m, us) -> MessageDenormalizationState.builder()
//                                .key(m.getKey())
//                                .eventNumber(UUID.randomUUID().toString().replace("-", ""))
//                                .text(m.getText())
//                                .sender(m.getSender())
//                                .recipient(new User(us.getKey(), us.getFirstName(), us.getLastName()))
//                                .build(),
//                        JoinWindows.of(TimeUnit.MINUTES.toMillis(1)), Joined.with(Serdes.String(), messageStateSenderSerde, userStateSerde))
//                .to(getFeed(MessageDenormalizationState.class), Produced.with(Serdes.String(), messageDenormalizationStateSerde));
//
//        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfig());
//        streams.start();
//    }
//}
