package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.direct.message.core.content.MessageContent;
import com.evolution.direct.message.core.request.MessageCreateRequest;
import com.evolution.direct.message.core.request.MessageUpdateTextRequest;
import com.evolution.library.core.v5.MessageService;
import com.evolution.library.core.v5.Request;
import com.evolution.library.core.v5.RequestTransform;
import org.springframework.stereotype.Service;

@Service
public class MessageRequestTransform implements RequestTransform<String, MessageRequestTypes, MessageCommand> {

    @Override
    public MessageCommand transform(Request<String, MessageRequestTypes> request) {
        MessageRequestTypes type = request.getRequestType();

        String key = request.getKey();
        if (request.getKey() == null) {
            key = MessageService.random();
        }
        MessageCommand command = MessageCommand.builder().build();
        command.withContent(MessageContent.builder().build());

        switch (type) {
            case MessageUpdateIsReadRequst:
                command.getContent()
                        .withRead(true);
                break;
            case MessageUpdateTextRequest:
                MessageUpdateTextRequest messageUpdateTextRequest = (MessageUpdateTextRequest) request;
                command.getContent()
                        .withText(messageUpdateTextRequest.getText());
                break;
            case MessageCreateRequest:
                MessageCreateRequest messageCreateRequest = (MessageCreateRequest) request;
                command.getContent()
                        .withText(messageCreateRequest.getText())
                        .withSender(messageCreateRequest.getSender())
                        .withRecipient(messageCreateRequest.getRecipient())
                        .withRead(false);
                break;
        }

        return command
                .withKey(key)
                .withType(type)
                .withCorrelation(MessageService.random());
    }
}
