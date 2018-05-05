package com.evolution.user.command.controller;

import com.evolution.library.core.CommandRequestDTO;
import com.evolution.library.core.CommandRequestResponseBuilder;
import com.evolution.user.command.dto.UserCreateRequestDTO;
import com.evolution.user.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.command.dto.UserUpdateUsernameRequestDTO;
import com.evolution.user.command.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResourceSupport> post(@Valid @RequestBody UserCreateRequestDTO request) {
        CommandRequestDTO commandRequestDTO = CommandRequestResponseBuilder.build();
        userCommandService.postUser(commandRequestDTO.getOperationNumber(), request);
        return CommandRequestResponseBuilder.response(commandRequestDTO);
    }

    @PutMapping(value = "/update-username")
    public ResponseEntity<ResourceSupport> updateUsername(@Valid @RequestBody UserUpdateUsernameRequestDTO request) {
        CommandRequestDTO commandRequestDTO = CommandRequestResponseBuilder.build();
        userCommandService.updateUsername(commandRequestDTO.getOperationNumber(), request);
        return CommandRequestResponseBuilder.response(commandRequestDTO);

    }

    @PutMapping(value = "/update-first-name-last-name")
    public ResponseEntity<ResourceSupport> updateFirstNameLastName(@Valid @RequestBody UserUpdateFirstNameLastNameRequestDTO request) {
        CommandRequestDTO commandRequestDTO = CommandRequestResponseBuilder.build();
        userCommandService.updateFirstNameLastName(commandRequestDTO.getOperationNumber(), request);
        return CommandRequestResponseBuilder.response(commandRequestDTO);
    }

    @DeleteMapping(value = "/{key}")
    public ResponseEntity<ResourceSupport> delete(@PathVariable String key) {
        CommandRequestDTO commandRequestDTO = CommandRequestResponseBuilder.build();
        userCommandService.deleteUser(commandRequestDTO.getOperationNumber(), key);
        return CommandRequestResponseBuilder.response(commandRequestDTO);
    }
}
