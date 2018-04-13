package com.evolution.user.service;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserStateEvent;
import com.evolution.user.event.UserUpdateEvent;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class UserEventBuilder {

    public static UserStateEvent buildState(UserCreateEvent userCreateEvent, UserUpdateEvent userUpdateEvent) {
        if (userUpdateEvent != null
                && !userCreateEvent.getId().equals(userUpdateEvent.getId())) {
            throw new UnsupportedOperationException("Event id must be equals");
        }

        return UserStateEvent.builder()
                .id(userCreateEvent.getId())
                .username(userUpdateEvent == null ? userCreateEvent.getUsername() : (StringUtils.isEmpty(userUpdateEvent.getUsername()) ? userCreateEvent.getUsername() : userUpdateEvent.getUsername()))
                .password(userUpdateEvent == null ? userCreateEvent.getPassword() : (StringUtils.isEmpty(userUpdateEvent.getPassword()) ? userCreateEvent.getPassword() : userUpdateEvent.getPassword()))
                .firstName(userUpdateEvent == null ? userCreateEvent.getFirstName() : (StringUtils.isEmpty(userUpdateEvent.getFirstName()) ? userCreateEvent.getFirstName() : userUpdateEvent.getFirstName()))
                .lastName(userUpdateEvent == null ? userCreateEvent.getLastName() : (StringUtils.isEmpty(userUpdateEvent.getLastName()) ? userCreateEvent.getLastName() : userUpdateEvent.getLastName()))
                .nickname(userUpdateEvent == null ? userCreateEvent.getNickname() : (StringUtils.isEmpty(userUpdateEvent.getNickname()) ? userCreateEvent.getNickname() : userUpdateEvent.getNickname()))
                .datePost(userCreateEvent.getDatePost())
                .datePut(userUpdateEvent == null ? userCreateEvent.getDatePost() : userUpdateEvent.getDatePut())
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .build();
    }
}
