package com.evolution.user.layer.command.service;

import com.evolution.user.kafka.core.Event;
import com.evolution.user.layer.command.dto.UserCreateRequestDTO;
import com.evolution.user.layer.command.dto.UserUpdateRequestDTO;

public interface UserCommandService {

    void createUser(UserCreateRequestDTO request);

    void putUser(UserUpdateRequestDTO request);

    void postEvent(Event<String> event);
}
