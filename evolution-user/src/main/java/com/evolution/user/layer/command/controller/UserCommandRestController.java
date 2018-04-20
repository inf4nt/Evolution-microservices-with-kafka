package com.evolution.user.layer.command.controller;

import com.evolution.user.layer.command.dto.UserCreateRequestDTO;
import com.evolution.user.layer.command.dto.UserUpdateRequestDTO;
import com.evolution.user.layer.command.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/command")
public class UserCommandRestController {

    @Autowired
    private UserCommandService userCommandService;

    @PostMapping
    public void postUser(@RequestBody UserCreateRequestDTO requestDTO) {
        userCommandService.createUser(requestDTO);
    }

    @PutMapping
    public void putMapping(@RequestBody UserUpdateRequestDTO requestDTO) {
        userCommandService.putUser(requestDTO);
    }
}
