package com.evolution.direct.message.processor.service;

import com.evolution.direct.message.processor.event.CreateMessageEvent;
import com.evolution.direct.message.processor.event.MessageStateEvent;
import com.evolution.direct.message.processor.event.UpdateIsReadMessageEvent;
import com.evolution.direct.message.processor.event.UpdateTextMessageEvent;

public class MessageBuilder {

    public static MessageStateEvent buildState(CreateMessageEvent createMessageEvent, UpdateTextMessageEvent updateTextMessageEvent) {
        if (updateTextMessageEvent != null &&
                !updateTextMessageEvent.getId().equals(createMessageEvent.getId())) {
            throw new UnsupportedOperationException("Event id must be equals");
        }

        return MessageStateEvent.builder()
                .id(createMessageEvent.getId())
                .text(updateTextMessageEvent == null ? createMessageEvent.getText() : updateTextMessageEvent.getText())
                .sender(createMessageEvent.getSender())
                .recipient(createMessageEvent.getRecipient())
                .isRead(updateTextMessageEvent != null)
                .datePost(createMessageEvent.getDatePost())
                .datePut(updateTextMessageEvent == null ? createMessageEvent.getDatePost() : updateTextMessageEvent.getDatePut())
                .build();

    }



//    public static MessageStateEvent buildState(UpdateTextMessageEvent updateTextMessageEvent, UpdateIsReadMessageEvent updateIsReadMessageEvent) {
//        if (updateIsReadMessageEvent != null &&
//                !updateTextMessageEvent.getId().equals(updateIsReadMessageEvent.getId())) {
//            throw new UnsupportedOperationException("Event id must be equals");
//        }
//
//        return MessageStateEvent.builder()
//                .id(updateTextMessageEvent.getId())
//                .text(updateTextMessageEvent.getText())
//                .sender(updateTextMessageEvent.getSender())
//                .recipient(updateTextMessageEvent.getRecipient())
//                .datePost(updateTextMessageEvent.getDatePost())
//                .datePut(updateTextMessageEvent == null ? createMessageEvent.getDatePost() : updateTextMessageEvent.getDatePut())
//                .build();
//    }
}
