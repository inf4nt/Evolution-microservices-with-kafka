package com.evolution.user.core;

import com.evolution.library.core.v5.MessageService;
import com.evolution.library.core.v5.Request;
import com.evolution.library.core.v5.RequestTransform;
import com.evolution.user.core.common.UserRequestTypes;
import com.evolution.user.core.request.UserCreateRequest;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserRequestTransform implements RequestTransform<UserCommand, UserRequestTypes, UserCommand> {


    @Override
    public UserCommand transform(Request<UserCommand, UserRequestTypes> request) {
        return null;
    }


//    @Override
//    public UserCommand transform(Request request) {
//        UserRequestTypes types = (UserRequestTypes) request.getRequestType();
//
//        switch (types) {
//            case UserCreateRequest: {
//                UserCreateRequest r = (UserCreateRequest) request;
//                return UserCommand.builder()
//                        .operationNumber(MessageService.random())
//                        .correlation(MessageService.random())
//                        .requestType(r.getRequestType())
//                        .domain(UserDomain.builder()
//                                .key(r.getKey() == null ? MessageService.random() : r.getKey())
//                                .username(r.getUsername())
//                                .password(r.getPassword())
//                                .firstName(r.getFirstName())
//                                .lastName(r.getLastName())
//                                .build())
//                        .build();
//            }
//            case UserUpdateFirstNameRequest: {
//                UserUpdateFirstNameRequest r = (UserUpdateFirstNameRequest) request;
//                return UserCommand.builder()
//                        .correlation(MessageService.random())
//                        .operationNumber(MessageService.random())
//                        .requestType(r.getRequestType())
//                        .domain(UserDomain.builder()
//                                .key(r.getKey())
//                                .firstName(r.getFirstName())
//                                .build())
//                        .build();
//            }
//            default:
//                throw new UnsupportedOperationException();
//        }
//    }
}
