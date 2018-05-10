package com.evolution.user.processor;

import com.evolution.library.core.v3.MessageService;
import com.evolution.user.core.basic.UserCreateRequestCommand;
import com.evolution.user.core.basic.UserUpdateFirstNameRequestCommand;
import com.evolution.user.core.global.UserGlobalCommand;
import com.evolution.user.core.global.UserGlobalEvent;
import com.evolution.user.processor.bindings.UserCommandProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(UserCommandProcessor.class)
public class UserCommandStreamProcessor {

    @StreamListener(UserCommandProcessor.INPUT)
    @SendTo(UserCommandProcessor.OUTPUT)
    public Message<UserGlobalEvent> processEvent(UserGlobalCommand command) {
        System.out.println("processEvent:" + command);

        // todo handle event
        return MessageBuilder
                .withPayload(handle(command))
                .setHeader(KafkaHeaders.MESSAGE_KEY, command.getKey().getBytes())
                .build();
    }

    public UserGlobalEvent handle(UserGlobalCommand command) {
        String type = command.getMessageType();
        if (type.equals(MessageService.getMessageType(UserCreateRequestCommand.class))) {
            return UserGlobalEvent
                    .builder()
                    .messageType(command.getMessageType())
                    .key(command.getKey())
                    .eventNumber(command.getCommandNumber())
                    .correlation(MessageService.random())
                    .username(command.getUsername())
                    .password(command.getPassword())
                    .firstName(command.getFirstName())
                    .lastName(command.getLastName())
                    .build();
        } else if (type.equals(MessageService.getMessageType(UserUpdateFirstNameRequestCommand.class))) {
            return UserGlobalEvent
                    .builder()
                    .messageType(command.getMessageType())
                    .key(command.getKey())
                    .eventNumber(command.getCommandNumber())
                    .correlation(MessageService.random())
                    .firstName(command.getFirstName())
                    .build();
        } else {
            throw new UnsupportedOperationException("UserGlobalEvent handle");
        }
    }
}
