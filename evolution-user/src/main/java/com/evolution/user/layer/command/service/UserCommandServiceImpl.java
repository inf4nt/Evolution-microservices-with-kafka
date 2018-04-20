package com.evolution.user.layer.command.service;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserUpdateEvent;
import com.evolution.user.kafka.core.Event;
import com.evolution.user.layer.command.dto.UserCreateRequestDTO;
import com.evolution.user.layer.command.dto.UserUpdateRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private static final Logger logger = LoggerFactory.getLogger(UserCommandServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @Override
    public void createUser(UserCreateRequestDTO request) {
        // todo valid data and check username
        UserCreateEvent event = UserCreateEvent
                .builder()
                .id(UUID.randomUUID().toString().replaceAll("-", ""))
//                .id(request.getUsername())
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nickname(request.getNickname())
                .datePost(new Date())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();

        postEvent(event);
    }

    @Override
    public void putUser(UserUpdateRequestDTO request) {
        UserUpdateEvent event = UserUpdateEvent
                .builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nickname(request.getNickname())
                .build();

        postEvent(event);
    }

    @Override
    public void postEvent(Event<String> event) {
        logger.info("Send event:" + event);
        kafkaTemplate.send(event.getTopic(), event.getId(), event);
    }
}
