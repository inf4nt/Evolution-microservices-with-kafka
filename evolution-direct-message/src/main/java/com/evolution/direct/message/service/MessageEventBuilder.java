package com.evolution.direct.message.service;

import com.evolution.direct.message.event.*;
import com.evolution.direct.message.share.User;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class MessageEventBuilder {

    public static MessageTempStateEvent buildTemp(CreateMessageEvent createMessageEvent, UpdateMessageTextEvent updateMessageTextEvent) {
        return MessageTempStateEvent
                .builder()
                .id(createMessageEvent.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(updateMessageTextEvent == null ? createMessageEvent.getText() : updateMessageTextEvent.getText())
                .sender(createMessageEvent.getSender())
                .recipient(createMessageEvent.getRecipient())
                .isRead(updateMessageTextEvent != null)
                .postDate(createMessageEvent.getPostDate())
                .putDate(updateMessageTextEvent == null ? createMessageEvent.getPostDate() : updateMessageTextEvent.getPutDate())
                .supperId(createMessageEvent.getId())
                .build();
    }

    public static MessageStateEvent buildStateForSender(MessageTempStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
        User recipient = new User();
        recipient.setId(messageTempStateEvent.getRecipient());

        return MessageStateEvent
                .builder()
                .id(messageTempStateEvent.getSupperId())
                .text(messageTempStateEvent.getText())
                .sender(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .recipient(recipient)
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageTempStateEvent.isRead())
                .postDate(messageTempStateEvent.getPostDate())
                .putDate(messageTempStateEvent.getPutDate())
                .build();
    }

    public static MessageStateEvent buildStateForRecipient(MessageTempStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
        return MessageStateEvent
                .builder()
                .id(messageTempStateEvent.getSupperId())
                .text(messageTempStateEvent.getText())
                .recipient(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageTempStateEvent.isRead())
                .postDate(messageTempStateEvent.getPostDate())
                .putDate(messageTempStateEvent.getPutDate())
                .build();
    }

    public static MessageStateEvent build(MessageStateEvent s, MessageStateEvent r) {
        if (s.getSender() == null || r.getRecipient() == null) {
            throw new UnsupportedOperationException("Sender is null or recipient");
        }

        return MessageStateEvent
                .builder()
                .id(s.getId())
                .sender(s.getSender())
                .recipient(r.getRecipient())
                .text(s.getText())
                .postDate(s.getPostDate())
                .putDate(s.getPutDate())
                .isRead(s.isRead())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
    }
}
