package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageEventStatus;
import com.evolution.library.core.v5.CommandHandler;
import org.springframework.stereotype.Service;

@Service
public class MessageCommandHandler implements CommandHandler<MessageCommand, MessageEvent> {

    @Override
    public MessageEvent handle(MessageCommand command) {
        //todo validate command
        return MessageEvent.builder()
                .key(command.getKey())
                .correlation(command.getCorrelation())
                .eventStatus(MessageEventStatus.Progress)
                .type(command.getType())
                .content(command.getContent())
                .build();
    }
}
