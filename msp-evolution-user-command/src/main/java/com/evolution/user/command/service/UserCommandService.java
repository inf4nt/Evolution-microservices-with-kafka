package com.evolution.user.command.service;


import com.evolution.library.core.CommandRequestDTO;
import com.evolution.user.command.dto.UserCreateRequestDTO;
import com.evolution.user.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.command.dto.UserUpdateUsernameRequestDTO;

public interface UserCommandService {

    void postUser(CommandRequestDTO requestDTO);

    void postUser(UserCreateRequestDTO request);

    void postUser(String operationNumber, UserCreateRequestDTO request);

    void updateUsername(UserUpdateUsernameRequestDTO request);

    void updateFirstNameLastName(UserUpdateFirstNameLastNameRequestDTO request);
}
