package com.evolution.direct.message.processor;

import com.evolution.direct.message.core.MessageState;
import com.evolution.direct.message.core.domain.MessageDomain;
import com.evolution.direct.message.core.domain.User;
import com.evolution.direct.message.core.domain.UserDomain;
import com.evolution.direct.message.core.temp.MessageDomainWithSenderTemp;
import com.evolution.direct.message.processor.bindings.DomainProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(DomainProcessor.class)
public class MessageDomainProcessor {

    private final ObjectMapper objectMapper;

    @Autowired
    public MessageDomainProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @StreamListener
    @SendTo(DomainProcessor.OUTPUT)
    public KStream<String, MessageDomain> process(@Input(DomainProcessor.INPUT) KTable<String, MessageState> stateMessage,
                                                  @Input(DomainProcessor.INPUT_USER_DOMAIN) KTable<String, UserDomain> stateUser) {

        final Serde<MessageState> messageStateSerde = new JsonSerde<>(MessageState.class, objectMapper);
        final Serde<MessageDomainWithSenderTemp> messageDomainWithSenderTempSerde = new JsonSerde<>(MessageDomainWithSenderTemp.class, objectMapper);
        final Serde<User> userSerde = new JsonSerde<>(User.class, objectMapper);

        final KTable<String, User> userKTable = stateUser.mapValues(v -> User.builder()
                .key(v.getKey())
                .firstName(v.getFirstName())
                .lastName(v.getLastName())
                .build());

        return stateMessage
                .toStream()
                .selectKey((k, v) -> v.getContent().getSender())
                .join(userKTable, (ms, us) -> MessageDomainWithSenderTemp.builder()
                                .key(ms.getKey())
                                .eventNumber(ms.getEventNumber())
                                .sender(us)
                                .recipient(ms.getContent().getRecipient())
                                .isRead(ms.getContent().isRead())
                                .build(),
                        Joined.with(Serdes.String(), messageStateSerde, userSerde))
                .selectKey((k, v) -> v.getRecipient())
                .join(userKTable, (mt, us) -> MessageDomain.builder()
                                .key(mt.getKey())
                                .eventNumber(mt.getEventNumber())
                                .sender(mt.getSender())
                                .recipient(us)
                                .text(mt.getText())
                                .isRead(mt.isRead())
                                .build(),
                        Joined.with(Serdes.String(), messageDomainWithSenderTempSerde, userSerde));
    }
}
