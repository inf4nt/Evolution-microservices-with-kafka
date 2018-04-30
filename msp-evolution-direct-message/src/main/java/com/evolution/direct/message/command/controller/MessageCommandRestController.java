package com.evolution.direct.message.command.controller;

import com.evolution.direct.message.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.command.dto.MessageUpdateTextRequestDTO;
import com.evolution.direct.message.command.service.MessageServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/command")
public class MessageCommandRestController {

    @Autowired
    private MessageServiceCommand messageServiceCommand;

    @PostMapping
    public void post(@Valid @RequestBody MessageCreateRequestDTO requestDTO) {
        messageServiceCommand.postMessage(requestDTO);
    }

    @PutMapping
    public void put(@Valid @RequestBody MessageUpdateTextRequestDTO requestDTO) {
        messageServiceCommand.putText(requestDTO);
    }
}
