package com.evolution.direct.message.command.service;


import com.evolution.direct.message.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.command.dto.MessageUpdateTextRequestDTO;

public interface MessageServiceCommand {

    void postMessage(MessageCreateRequestDTO requestDTO);

    void putText(MessageUpdateTextRequestDTO requestDTO);
}
