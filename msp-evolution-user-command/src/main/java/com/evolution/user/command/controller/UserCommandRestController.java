package com.evolution.user.command.controller;

import com.evolution.user.command.dto.UserCreateRequestDTO;
import com.evolution.user.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.command.dto.UserUpdateUsernameRequestDTO;
import com.evolution.user.command.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/command")
public class UserCommandRestController {

    private final UserCommandService userCommandService;

    @Autowired
    public UserCommandRestController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public void post(@Valid @RequestBody UserCreateRequestDTO request) {
        userCommandService.postUser(request);
    }

    @PostMapping(value = "/update-username")
    public void updateUsername(@Valid @RequestBody UserUpdateUsernameRequestDTO request) {
        userCommandService.updateUsername(request);
    }

    @PostMapping(value = "/update-first-name-last-name")
    public void updateFirstNameLastName(@Valid @RequestBody UserUpdateFirstNameLastNameRequestDTO request) {
        userCommandService.updateFirstNameLastName(request);
    }
}
