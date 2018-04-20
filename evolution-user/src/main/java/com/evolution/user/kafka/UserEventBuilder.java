package com.evolution.user.kafka;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserStateEvent;
import com.evolution.user.event.UserUpdateEvent;

import java.util.UUID;

public class UserEventBuilder {

    public static UserStateEvent build(String key, UserStateEvent state, UserUpdateEvent update) {
        UserStateEvent event =  UserStateEvent
                .builder()
                .id(key)
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .firstName(update.getFirstName())
                .lastName(update.getLastName())
                .nickname(update.getNickname())
                .username(state.getUsername())
                .password(state.getPassword())
                .datePost(state.getDatePost())
                .datePut(update.getDatePut())
                .build();
        return event;
    }

    public static UserStateEvent build(String key, UserStateEvent state) {
        UserStateEvent event =  UserStateEvent
                .builder()
                .id(key)
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .firstName(state.getFirstName())
                .lastName(state.getLastName())
                .nickname(state.getNickname())
                .username(state.getUsername())
                .password(state.getPassword())
                .datePost(state.getDatePost())
                .datePut(state.getDatePut())
                .build();
        return event;
    }

    public static UserStateEvent build(String key, UserCreateEvent create) {
        return UserStateEvent
                .builder()
                .id(key)
                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
                .firstName(create.getFirstName())
                .lastName(create.getLastName())
                .nickname(create.getNickname())
                .username(create.getUsername())
                .password(create.getPassword())
                .datePost(create.getDatePost())
                .datePut(create.getDatePost())
                .build();
    }

//    public static UserStateEvent build(UserStateEvent oldState, UserUpdateEvent update) {
//        return UserStateEvent
//                .builder()
//                .id(oldState.getId())
//                .eventId(UUID.randomUUID().toString().replaceAll("-", ""))
//                .firstName(update == null ? oldState.getFirstName() : update.getFirstName())
//                .lastName(update == null ? oldState.getLastName() : update.getLastName())
//                .nickname(update == null ? oldState.getNickname() : update.getNickname())
//                .username(update == null ? oldState.getNickname() : update.getNickname())
//                .password(update == null ? oldState.getPassword() : update.getPassword())
//                .datePost(oldState.getDatePost())
//                .datePut(update == null ? oldState.getDatePost() : update.getDatePut())
//                .build();
//    }
}
