package com.evolution.user.command.service;

import com.evolution.core.command.UserCreateCommand;
import com.evolution.core.command.UserDeleteCommand;
import com.evolution.core.command.UserUpdateFirstNameLastNameCommand;
import com.evolution.library.core.Base;
import com.evolution.library.core.CommandRequestDTO;
import com.evolution.user.command.dto.UserCreateRequestDTO;
import com.evolution.user.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.command.dto.UserUpdateUsernameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

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
    public void updateUsername(String operationNumber, UserUpdateUsernameRequestDTO request) {

    }

    @Override
    public void updateFirstNameLastName(String operationNumber, UserUpdateFirstNameLastNameRequestDTO request) {
        UserUpdateFirstNameLastNameCommand command = UserUpdateFirstNameLastNameCommand.builder()
                .key(request.getKey())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .operationNumber(operationNumber)
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

    @Override
    public void deleteUser(String operationNumber, String key) {
        UserDeleteCommand command = UserDeleteCommand.builder()
                .key(key)
                .operationNumber(operationNumber)
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }
}
