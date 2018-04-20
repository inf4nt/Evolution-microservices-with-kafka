package com.evolution.direct.message.layer.command.service;

import com.evolution.direct.message.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.layer.command.dto.MessageUpdateTextRequestDTO;

public interface MessageCommandService {

    void postMessage(MessageCreateRequestDTO requestDTO);

    void putMessage(MessageUpdateTextRequestDTO request);
}
