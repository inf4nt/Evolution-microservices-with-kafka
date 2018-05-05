package com.evolution.user.command.service;

import com.evolution.core.command.UserCreateCommand;
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
    public void postUser(CommandRequestDTO requestDTO) {
        if (requestDTO.getRequest().getClass() != UserCreateRequestDTO.class) {
            throw new UnsupportedOperationException();
        }

        UserCreateRequestDTO request  = (UserCreateRequestDTO) requestDTO.getRequest();

        UserCreateCommand command = UserCreateCommand.builder()
                .key(UUID.randomUUID().toString().replace("-", ""))
                .operationNumber(requestDTO.getOperationNumber())
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

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
