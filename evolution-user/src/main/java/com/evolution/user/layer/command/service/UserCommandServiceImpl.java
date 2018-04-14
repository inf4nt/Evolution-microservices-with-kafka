package com.evolution.user.layer.command.service;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserEvent;
import com.evolution.user.layer.command.dto.UserCreateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @Override
    public void createUser(UserCreateRequestDTO request) {
        // todo valid data and check username
        UserCreateEvent event = UserCreateEvent
                .builder()
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nickname(request.getNickname())
                .datePost(new Date())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();

        kafkaTemplate.send(event.getTopic(), event.getId(), event);
    }
}
