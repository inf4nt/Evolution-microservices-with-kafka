package com.evolution.direct.message.command.service;


import com.evolution.core.command.MessageCreateCommand;
import com.evolution.core.command.MessageUpdateTextCommand;
import com.evolution.direct.message.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.command.dto.MessageUpdateTextRequestDTO;
import com.evolution.library.core.Base;
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
                .operationNumber(UUID.randomUUID().toString().replace("-", ""))
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
                .operationNumber(UUID.randomUUID().toString().replace("-", ""))
                .text(requestDTO.getText())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }
}
