package com.evolution.direct.message.base.layer.command.service;

import com.evolution.direct.message.base.core.command.MessageCreateCommand;
import com.evolution.direct.message.base.core.command.MessageUpdateTextCommand;
import com.evolution.direct.message.base.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.base.layer.command.dto.MessageUpdateTextRequestDTO;
import com.evolution.direct.message.core.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServiceCommandImpl implements MessageServiceCommand {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

    @Override
    public void postMessage(MessageCreateRequestDTO requestDTO) {
        MessageCreateCommand command = MessageCreateCommand
                .builder()
                .key(UUID.randomUUID().toString().replace("-", ""))
                .text(requestDTO.getText())
                .sender(requestDTO.getSender())
                .recipient(requestDTO.getRecipient())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

    @Override
    public void putText(MessageUpdateTextRequestDTO requestDTO) {
        MessageUpdateTextCommand command = MessageUpdateTextCommand
                .builder()
                .key(requestDTO.getKey())
                .text(requestDTO.getText())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }
}
