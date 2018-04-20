package com.evolution.direct.message.service;

import com.evolution.direct.message.event.*;
import com.evolution.direct.message.event.temp.MessageDenormalizationStateSenderAndRecipientTemp;
import com.evolution.direct.message.event.temp.MessageDenormalizationStateSenderTemp;
import com.evolution.direct.message.event.MessageStateEvent;
import com.evolution.direct.message.share.User;

import java.util.UUID;

public class MessageEventBuilder {

    public static MessageDenormalizationStateSenderTemp build(MessageStateEvent messageStateEvent, UserStateEvent userStateEvent) {
        return MessageDenormalizationStateSenderTemp
                .builder()
                .id(messageStateEvent.getId())
                .text(messageStateEvent.getText())
                .recipient(messageStateEvent.getRecipient())
                .sender(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageStateEvent.isRead())
                .postDate(messageStateEvent.getPostDate())
                .putDate(messageStateEvent.getPutDate())
                .build();
    }

    public static MessageDenormalizationStateSenderAndRecipientTemp build(MessageDenormalizationStateSenderTemp messageDenormalizationStateSenderTemp, UserStateEvent userStateEvent) {
        return MessageDenormalizationStateSenderAndRecipientTemp
                .builder()
                .id(messageDenormalizationStateSenderTemp.getId())
                .text(messageDenormalizationStateSenderTemp.getText())
                .recipient(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .sender(messageDenormalizationStateSenderTemp.getSender())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageDenormalizationStateSenderTemp.isRead())
                .postDate(messageDenormalizationStateSenderTemp.getPostDate())
                .putDate(messageDenormalizationStateSenderTemp.getPutDate())
                .build();
    }

    public static MessageDenormalizationStateEvent build(MessageDenormalizationStateSenderAndRecipientTemp event) {
        return MessageDenormalizationStateEvent.builder()
                .id(event.getId())
                .text(event.getText())
                .sender(event.getSender())
                .recipient(event.getRecipient())
                .isRead(event.isRead())
                .postDate(event.getPostDate())
                .putDate(event.getPutDate())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
    }

    public static MessageStateEvent build(MessageCreateEvent createEvent, MessageUpdateTextEvent updateTextEvent) {
        return MessageStateEvent.builder()
                .id(createEvent.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .sender(createEvent.getSender())
                .recipient(createEvent.getRecipient())
                .postDate(createEvent.getPostDate())
                .text(updateTextEvent == null ? createEvent.getText() : updateTextEvent.getText())
                .putDate(updateTextEvent == null ? createEvent.getPostDate() : updateTextEvent.getPutDate())
                .build();
    }
}
