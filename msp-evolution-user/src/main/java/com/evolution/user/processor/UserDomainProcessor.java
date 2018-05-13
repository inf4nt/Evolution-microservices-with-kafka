package com.evolution.user.processor;

import com.evolution.user.core.UserDomain;
import com.evolution.user.core.UserState;
import com.evolution.user.processor.bindings.DomainProcessor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(DomainProcessor.class)
public class UserDomainProcessor {

    @StreamListener(DomainProcessor.INPUT)
    @SendTo(DomainProcessor.OUTPUT)
    public KStream<String, UserDomain> process(KStream<String, UserState> input) {
        return input.map((k, v) -> new KeyValue<>(k, UserDomain.builder()
                .key(k)
                .eventNumber(v.getEventNumber())
                .username(v.getContent().getUsername())
                .password(v.getContent().getPassword())
                .nickname(v.getContent().getNickname())
                .firstName(v.getContent().getFirstName())
                .lastName(v.getContent().getLastName())
                .build()));
    }
}
