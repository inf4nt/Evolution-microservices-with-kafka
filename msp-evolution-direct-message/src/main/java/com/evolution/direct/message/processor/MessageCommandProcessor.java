package com.evolution.direct.message.processor;

import com.evolution.direct.message.core.MessageCommand;
import com.evolution.direct.message.core.MessageCommandHandler;
import com.evolution.direct.message.core.MessageEvent;
import com.evolution.direct.message.processor.bindings.CommandProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(CommandProcessor.class)
public class MessageCommandProcessor {

    private final MessageCommandHandler messageCommandHandler;

    @Autowired
    public MessageCommandProcessor(MessageCommandHandler messageCommandHandler) {
        this.messageCommandHandler = messageCommandHandler;
    }

    @StreamListener(CommandProcessor.INPUT)
    @SendTo(CommandProcessor.OUTPUT)
    public Message<MessageEvent> process(MessageCommand command) {
        return MessageBuilder
                .withPayload(messageCommandHandler.handle(command))
                .setHeader(KafkaHeaders.MESSAGE_KEY, command.getKey().getBytes())
                .build();
    }
}
