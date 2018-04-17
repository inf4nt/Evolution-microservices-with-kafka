package com.evolution.direct.message.service;

import com.evolution.direct.message.event.*;
import com.evolution.direct.message.share.User;

import java.util.UUID;

public class MessageEventBuilder {

    public static MessageDenormalizationStateEvent build(MessageDenormalizationStateEvent s, MessageDenormalizationStateEvent r) {
        return MessageDenormalizationStateEvent.builder()
                .id(s.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(s.getText())
                .sender(s.getSender())

                .recipient(r.getRecipient())

                .postDate(s.getPostDate())
                .putDate(s.getPutDate())
                .isRead(s.isRead())

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

    public static MessageDenormalizationStateEvent buildStateForSender(MessageStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
        User recipient = new User();
        recipient.setId(messageTempStateEvent.getRecipient());

        return MessageDenormalizationStateEvent
                .builder()
                .id(messageTempStateEvent.getId())
                .text(messageTempStateEvent.getText())
                .recipient(recipient)
                .sender(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageTempStateEvent.isRead())
                .postDate(messageTempStateEvent.getPostDate())
                .putDate(messageTempStateEvent.getPutDate())
                .build();
    }

    public static MessageDenormalizationStateEvent buildStateForRecipient(MessageStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
        return MessageDenormalizationStateEvent
                .builder()
                .id(messageTempStateEvent.getId())
                .text(messageTempStateEvent.getText())
                .recipient(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageTempStateEvent.isRead())
                .postDate(messageTempStateEvent.getPostDate())
                .putDate(messageTempStateEvent.getPutDate())
                .build();
    }

    public static MessageDenormalizationStateEvent buildStateForRecipient2(MessageDenormalizationStateEvent messageDenormalizationStateEvent, UserStateEvent userStateEvent) {
        return MessageDenormalizationStateEvent
                .builder()
                .id(messageDenormalizationStateEvent.getId())
                .text(messageDenormalizationStateEvent.getText())
                .sender(messageDenormalizationStateEvent.getSender())
                .recipient(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .isRead(messageDenormalizationStateEvent.isRead())
                .postDate(messageDenormalizationStateEvent.getPostDate())
                .putDate(messageDenormalizationStateEvent.getPutDate())
                .build();
    }


//    public static MessageTempStateEvent buildTemp(MessageCreateEvent messageCreateEvent, MessageUpdateTextEvent messageUpdateTextEvent) {
//        return MessageTempStateEvent
//                .builder()
//                .id(messageCreateEvent.getId())
//                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
//                .text(messageUpdateTextEvent == null ? messageCreateEvent.getText() : messageUpdateTextEvent.getText())
//                .sender(messageCreateEvent.getSender())
//                .recipient(messageCreateEvent.getRecipient())
//                .isRead(messageUpdateTextEvent != null)
//                .postDate(messageCreateEvent.getPostDate())
//                .putDate(messageUpdateTextEvent == null ? messageCreateEvent.getPostDate() : messageUpdateTextEvent.getPutDate())
//                .supperId(messageCreateEvent.getId())
//                .build();
//    }
//
//    public static MessageStateEvent buildStateForSender(MessageTempStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
//        User recipient = new User();
//        recipient.setId(messageTempStateEvent.getRecipient());
//
//        return MessageStateEvent
//                .builder()
//                .id(messageTempStateEvent.getSupperId())
//                .text(messageTempStateEvent.getText())
//                .sender(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
//                .recipient(recipient)
//                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
//                .isRead(messageTempStateEvent.isRead())
//                .postDate(messageTempStateEvent.getPostDate())
//                .putDate(messageTempStateEvent.getPutDate())
//                .build();
//    }
//
//    public static MessageStateEvent buildStateForRecipient(MessageTempStateEvent messageTempStateEvent, UserStateEvent userStateEvent) {
//        return MessageStateEvent
//                .builder()
//                .id(messageTempStateEvent.getSupperId())
//                .text(messageTempStateEvent.getText())
//                .recipient(new User(userStateEvent.getId(), userStateEvent.getFirstName(), userStateEvent.getLastName(), userStateEvent.getNickname()))
//                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
//                .isRead(messageTempStateEvent.isRead())
//                .postDate(messageTempStateEvent.getPostDate())
//                .putDate(messageTempStateEvent.getPutDate())
//                .build();
//    }
//
//    public static MessageStateEvent build(MessageStateEvent s, MessageStateEvent r) {
//        if (s.getSender() == null || r.getRecipient() == null) {
//            throw new UnsupportedOperationException("Sender is null or recipient");
//        }
//
//        return MessageStateEvent
//                .builder()
//                .id(s.getId())
//                .sender(s.getSender())
//                .recipient(r.getRecipient())
//                .text(s.getText())
//                .postDate(s.getPostDate())
//                .putDate(s.getPutDate())
//                .isRead(s.isRead())
//                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
//                .build();
//    }
}
