package com.evolution.direct.message.layer.command.controller;

import com.evolution.direct.message.layer.command.dto.MessageCreateRequestDTO;
import com.evolution.direct.message.layer.command.dto.MessageUpdateTextRequestDTO;
import com.evolution.direct.message.layer.command.service.MessageCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/command")
public class MessageCommandRestController {

    @Autowired
    private MessageCommandService messageCommandService;

    @PostMapping
    public void postMessage(@RequestBody MessageCreateRequestDTO requestDTO) {
        messageCommandService.postMessage(requestDTO);
    }

    @PutMapping
    public void putMessage(@RequestBody MessageUpdateTextRequestDTO requestDTO) {
        messageCommandService.putMessage(requestDTO);
    }
}
