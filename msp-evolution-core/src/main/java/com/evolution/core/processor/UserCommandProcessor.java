package com.evolution.core.processor;

import com.evolution.core.aggregator.UserEventAggregator;
import com.evolution.core.command.UserCreateCommand;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import javax.validation.Valid;

import static com.evolution.library.core.v2.EventSourcingService.generateRundom;

@EnableBinding(Processor.class)
public class UserCommandProcessor {

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Message<UserEventAggregator> processEvent(@Valid UserCreateCommand command) {
        System.out.println("UserCommandProcessor");

        UserEventAggregator a = UserEventAggregator.builder()
                .key(command.getKey())
                .correlationId(generateRundom())
                .commandNumber(command.getCorrelationId())
                .eventType(command.getClass().getSimpleName())
                .username(command.getUsername())
                .password(command.getPassword())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();

        return MessageBuilder
                .withPayload(a)
                .setHeader(KafkaHeaders.MESSAGE_KEY, a.getKey().getBytes())
                .build();
    }
}
