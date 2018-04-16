package com.evolution.direct.message.processor.service;

import com.evolution.direct.message.processor.event.MessageCreateEvent;
import com.evolution.direct.message.processor.event.MessageStateEvent;
import com.evolution.direct.message.processor.event.MessageUpdateTextEvent;

import java.util.UUID;

public class MessageEventBuilder {

    public static MessageStateEvent buildState(MessageCreateEvent createEvent, MessageUpdateTextEvent updateTextEvent) {
        return MessageStateEvent
                .builder()
                .id(createEvent.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(updateTextEvent == null ? createEvent.getText() : updateTextEvent.getText())
                .sender(createEvent.getSender())
                .recipient(createEvent.getRecipient())
                .postDate(createEvent.getPostDate())
                .putDate(updateTextEvent == null ? null : updateTextEvent.getPutDate())
                .isRead(false)
                .build();
    }
}
