package com.evolution.direct.message.processor.service;

import com.evolution.direct.message.processor.event.*;
import com.evolution.direct.message.processor.share.User;

import java.util.UUID;

public class MessageEventBuilder {

    public static MessageDenormalizationStateEvent build(MessageDenormalizationStateEvent s, MessageDenormalizationStateEvent r) {
        return MessageDenormalizationStateEvent
                .builder()
                .id(s.getSuperId())
                .superId(s.getSuperId())
                .eventId(s.getEventId())
                .sender(s.getSender())

                .recipient(r.getRecipient()) // recipient

                .text(s.getText())
                .postDate(s.getPostDate())
                .putDate(s.getPutDate())
                .isRead(s.isRead())
                .build();
    }

    public static MessageDenormalizationStateEvent rebuild(MessageDenormalizationStateEvent event) {
        return MessageDenormalizationStateEvent
                .builder()
                .id(event.getSuperId())
                .superId(event.getSuperId())
                .eventId(event.getEventId())
                .sender(event.getSender())
                .recipient(event.getRecipient())
                .text(event.getText())
                .postDate(event.getPostDate())
                .putDate(event.getPutDate())
                .isRead(event.isRead())
                .build();
    }

    public static MessageStateEvent buildForMap(MessageStateEvent messageStateEvent) {
        return MessageStateEvent.builder()
                .id(messageStateEvent.getId())
                .superId(messageStateEvent.getId())
                .text(messageStateEvent.getText())
                .sender(messageStateEvent.getSender())
                .recipient(messageStateEvent.getRecipient())
                .postDate(messageStateEvent.getPostDate())
                .putDate(messageStateEvent.getPutDate())
                .isRead(messageStateEvent.isRead())
                .build();
    }

    public static MessageStateEvent buildState(MessageCreateEvent createEvent, MessageUpdateTextEvent updateTextEvent) {
        return MessageStateEvent
                .builder()
                .id(createEvent.getId())
                .superId(createEvent.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(updateTextEvent == null ? createEvent.getText() : updateTextEvent.getText())
                .sender(createEvent.getSender())
                .recipient(createEvent.getRecipient())
                .postDate(createEvent.getPostDate())
                .putDate(updateTextEvent == null ? null : updateTextEvent.getPutDate())
                .isRead(false)
                .build();
    }

    public static MessageDenormalizationStateEvent buildForSender(MessageStateEvent message, UserStateEvent user) {
        User sender = new User();
        sender.setId(user.getId());
        sender.setFirstName(user.getFirstName());
        sender.setLastName(user.getLastName());
        sender.setNickname(user.getNickname());

        return MessageDenormalizationStateEvent
                .builder()
                .id(message.getId())
                .superId(message.getSuperId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .sender(sender)
                .text(message.getText())
                .postDate(message.getPostDate())
                .putDate(message.getPutDate())
                .isRead(message.isRead())
                .build();
    }

    public static MessageDenormalizationStateEvent buildForRecipient(MessageStateEvent message, UserStateEvent user) {
        User recipient = new User();
        recipient.setId(user.getId());
        recipient.setFirstName(user.getFirstName());
        recipient.setLastName(user.getLastName());
        recipient.setNickname(user.getNickname());

        return MessageDenormalizationStateEvent
                .builder()
                .id(message.getId())
                .superId(message.getSuperId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .recipient(recipient)
                .text(message.getText())
                .postDate(message.getPostDate())
                .putDate(message.getPutDate())
                .isRead(message.isRead())
                .build();
    }
}
