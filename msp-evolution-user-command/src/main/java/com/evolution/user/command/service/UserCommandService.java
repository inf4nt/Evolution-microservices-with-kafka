package com.evolution.user.command.service;


import com.evolution.user.command.dto.UserCreateRequestDTO;
import com.evolution.user.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.command.dto.UserUpdateUsernameRequestDTO;

public interface UserCommandService {

    void postUser(String operationNumber, UserCreateRequestDTO request);

    void updateUsername(String operationNumber, UserUpdateUsernameRequestDTO request);

    void updateFirstNameLastName(String operationNumber, UserUpdateFirstNameLastNameRequestDTO request);

    void deleteUser(String operationNumber, String key);
}
