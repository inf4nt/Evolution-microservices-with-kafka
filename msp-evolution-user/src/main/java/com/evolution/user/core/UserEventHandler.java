package com.evolution.user.core;

import com.evolution.library.core.v5.EventHandler;
import com.evolution.library.core.v5.MessageService;
import com.evolution.user.core.common.UserRequestTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserEventHandler implements EventHandler<UserEvent, UserState> {

    private static final Logger logger = LoggerFactory.getLogger(UserEventHandler.class);

    @Override
    public UserState handle(UserEvent event, UserState state) {
        logger.info("Handle event:" + event);
        UserRequestTypes type = event.getType();

        switch (type) {
            case UserCreateRequest:
                return UserState.builder()
                        .key(event.getKey())
                        .operationNumber(event.getOperationNumber())
                        .content(UserContent.builder()
                                .username(event.getContent().getUsername())
                                .password(event.getContent().getPassword())
                                .firstName(event.getContent().getFirstName())
                                .lastName(event.getContent().getLastName())
                                .nickname(event.getContent().getNickname())
                                .build())
                        .build();
            case UserUpdateFirstNameRequest:
                return state.withContent(state.getContent().withFirstName(event.getContent().getFirstName()))
                        .withOperationNumber(event.getOperationNumber());
            default:
                throw new UnsupportedOperationException("Not found request type !");
        }
    }
}
