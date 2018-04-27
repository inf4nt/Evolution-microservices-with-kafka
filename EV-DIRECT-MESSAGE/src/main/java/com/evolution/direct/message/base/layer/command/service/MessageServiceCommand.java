package com.evolution.direct.message.base.layer.command.service;

import com.evolution.direct.message.base.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.base.layer.command.dto.MessageUpdateTextRequestDTO;

public interface MessageServiceCommand {

    void postMessage(MessageCreateRequestDTO requestDTO);

    void putText(MessageUpdateTextRequestDTO requestDTO);
}
