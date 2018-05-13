package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v5.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class MessageEventHandler implements EventHandler<MessageEvent, MessageState> {

    @Override
    public MessageState handle(MessageEvent event, MessageState state) {
        MessageRequestTypes type = event.getType();

        switch (type) {
            case MessageCreateRequest:
                state = MessageState.builder().build();
                state.withContent(event.getContent());
                break;
            case MessageUpdateTextRequest:
                state.getContent()
                        .withText(event.getContent().getText());
                break;
            case MessageUpdateIsReadRequst:
                state.getContent()
                        .withRead(true);
                break;
        }

        return state
                .withKey(event.getKey())
                .withEventNumber(event.getCorrelation());
    }
}
