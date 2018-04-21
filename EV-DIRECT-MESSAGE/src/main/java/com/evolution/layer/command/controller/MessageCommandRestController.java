package com.evolution.layer.command.controller;

import com.evolution.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.layer.command.dto.MessageUpdateTextRequestDTO;
import com.evolution.layer.command.service.MessageServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/command")
public class MessageCommandRestController {

    @Autowired
    private MessageServiceCommand messageServiceCommand;

    @PostMapping
    public void post(@RequestBody MessageCreateRequestDTO requestDTO) {
        messageServiceCommand.postMessage(requestDTO);
    }

    @PutMapping
    public void put(@RequestBody MessageUpdateTextRequestDTO requestDTO) {
        messageServiceCommand.putText(requestDTO);
    }
}
