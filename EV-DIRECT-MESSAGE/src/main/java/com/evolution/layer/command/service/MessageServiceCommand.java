package com.evolution.layer.command.service;

import com.evolution.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.layer.command.dto.MessageUpdateTextRequestDTO;

public interface MessageServiceCommand {

    void postMessage(MessageCreateRequestDTO requestDTO);

    void putText(MessageUpdateTextRequestDTO requestDTO);
}
