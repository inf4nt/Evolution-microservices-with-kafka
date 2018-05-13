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
        return null;
    }

//    @Override
//    public UserState handle(@Valid UserEvent event, @Valid UserState state) {
//        UserRequestTypes type = event.getRequestType();
//        UserState result;
//
//        switch (type) {
//            case UserCreateRequest: {
//                result = UserState.builder()
//                        .domain(event.getDomain())
//                        .build();
//                break;
//            }
//            case UserUpdateFirstNameRequest: {
//                result = state
//                        .withDomain(state.getDomain().withFirstName(event.getDomain().getFirstName()));
//                break;
//            }
//            default:
//                throw new UnsupportedOperationException("Not found request type by " + type);
//        }
//
//        return result
//                .withRequestType(event.getRequestType())
//                .withOperationNumber(event.getOperationNumber())
//                .withCorrelation(MessageService.random());
//    }
}
