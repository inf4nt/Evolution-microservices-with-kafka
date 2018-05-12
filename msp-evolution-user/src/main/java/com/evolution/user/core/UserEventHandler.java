package com.evolution.user.core;

import com.evolution.library.core.v4.EventHandler;
import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.common.UserRequestTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserEventHandler implements EventHandler<UserEvent, UserStateEvent> {

    private static final Logger logger = LoggerFactory.getLogger(UserEventHandler.class);

    @Override
    public UserStateEvent handle(@Valid UserEvent event, @Valid UserStateEvent state) {
        UserRequestTypes type = event.getRequestType();
        UserStateEvent result;

        switch (type) {
            case UserCreateRequest: {
                result = UserStateEvent.builder()
                        .domain(event.getDomain())
                        .build();
                break;
            }
            case UserUpdateFirstNameRequest: {
                result = state
                        .withDomain(state.getDomain().withFirstName(event.getDomain().getFirstName()));
                break;
            }
            default:
                throw new UnsupportedOperationException("Not found request type by " + type);
        }

        return result
                .withRequestType(event.getRequestType())
                .withOperationNumber(event.getOperationNumber())
                .withCorrelation(MessageService.random());
    }
}
