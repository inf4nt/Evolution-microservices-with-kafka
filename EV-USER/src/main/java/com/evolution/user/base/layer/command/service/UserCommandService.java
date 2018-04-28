package com.evolution.user.base.layer.command.service;

import com.evolution.user.base.layer.command.dto.UserCreateRequestDTO;
import com.evolution.user.base.layer.command.dto.UserUpdateFirstNameLastNameRequestDTO;
import com.evolution.user.base.layer.command.dto.UserUpdateUsernameRequestDTO;

public interface UserCommandService {

    void postUser(UserCreateRequestDTO request);

    void updateUsername(UserUpdateUsernameRequestDTO request);

    void updateFirstNameLastName(UserUpdateFirstNameLastNameRequestDTO request);
}
