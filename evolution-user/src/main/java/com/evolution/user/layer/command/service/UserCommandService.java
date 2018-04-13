package com.evolution.user.layer.command.service;

import com.evolution.user.layer.command.dto.UserCreateRequestDTO;

public interface UserCommandService {

    void createUser(UserCreateRequestDTO request);
}
