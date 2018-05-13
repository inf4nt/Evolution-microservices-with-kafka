package com.evolution.user.processor;

import com.evolution.user.core.UserCommand;
import com.evolution.user.core.UserCommandHandler;
import com.evolution.user.core.UserEvent;
import com.evolution.user.processor.bindings.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import javax.validation.Valid;

@EnableBinding(CommandProcessor.class)
public class UserCommandProcessor {

    private final UserCommandHandler userCommandHandler;

    @Autowired
    public UserCommandProcessor(UserCommandHandler userCommandHandler) {
        this.userCommandHandler = userCommandHandler;
    }

    @StreamListener(CommandProcessor.INPUT)
    @SendTo(CommandProcessor.OUTPUT)
    public Message<UserEvent> process(@Valid UserCommand command) {
        return MessageBuilder
                .withPayload(userCommandHandler.handle(command))
                .setHeader(KafkaHeaders.MESSAGE_KEY, command.getKey().getBytes())
                .build();
    }
}
