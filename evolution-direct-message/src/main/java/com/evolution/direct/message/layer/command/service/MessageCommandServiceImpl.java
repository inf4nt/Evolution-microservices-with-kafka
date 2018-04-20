package com.evolution.direct.message.layer.command.service;

import com.evolution.direct.message.event.MessageCreateEvent;
import com.evolution.direct.message.event.MessageEvent;
import com.evolution.direct.message.event.MessageUpdateTextEvent;
import com.evolution.direct.message.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.layer.command.dto.MessageUpdateTextRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class MessageCommandServiceImpl implements MessageCommandService {

    @Autowired
    private KafkaTemplate<String, MessageEvent> kafkaTemplate;

    @Override
    public void postMessage(MessageCreateRequestDTO requestDTO) {
        MessageCreateEvent event = MessageCreateEvent.builder()
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(requestDTO.getText())
                .sender(requestDTO.getSender())
                .recipient(requestDTO.getRecipient())
                .postDate(new Date())
                .build();

        kafkaTemplate.send(event.getTopic(), event.getId(), event);
    }

    @Override
    public void putMessage(MessageUpdateTextRequestDTO request) {
        MessageUpdateTextEvent event = MessageUpdateTextEvent.builder()
                .id(request.getId())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .text(request.getText())
                .putDate(new Date())
                .build();

        kafkaTemplate.send(event.getTopic(), event.getId(), event);
    }
}
