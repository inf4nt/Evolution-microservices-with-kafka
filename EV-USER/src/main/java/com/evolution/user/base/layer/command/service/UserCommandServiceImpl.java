package com.evolution.user.base.layer.command.service;

import com.evolution.user.base.core.command.UserCreateCommand;
import com.evolution.user.base.layer.command.dto.UserCreateRequestDTO;
import com.evolution.user.base.layer.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.base.layer.command.dto.UserUpdateUsernameRequestDTO;
import com.evolution.user.core.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

    @Override
    public void postUser(UserCreateRequestDTO request) {
        UserCreateCommand command = UserCreateCommand.builder()
                .key(UUID.randomUUID().toString().replace("-", ""))
                .operationNumber(UUID.randomUUID().toString().replace("-", ""))
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

    @Override
    public void postUser(String operationNumber, UserCreateRequestDTO request) {
        UserCreateCommand command = UserCreateCommand.builder()
                .key(UUID.randomUUID().toString().replace("-", ""))
                .operationNumber(operationNumber)
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

    @Override
    public void updateUsername(UserUpdateUsernameRequestDTO request) {

    }

    @Override
    public void updateFirstNameLastName(UserUpdateFirstNameLastNameRequestDTO request) {

    }
}
