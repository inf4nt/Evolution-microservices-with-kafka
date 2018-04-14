package com.evolution.direct.message.layer.command.service;

import com.evolution.direct.message.layer.command.dto.MessageCreateRequestDTO;

public interface MessageCommandService {

    void postMessage(MessageCreateRequestDTO requestDTO);
}
