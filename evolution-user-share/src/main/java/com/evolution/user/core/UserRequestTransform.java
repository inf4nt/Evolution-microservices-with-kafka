package com.evolution.user.core;

import com.evolution.library.core.v5.MessageService;
import com.evolution.library.core.v5.Request;
import com.evolution.library.core.v5.RequestTransform;
import com.evolution.user.core.common.UserRequestTypes;
import com.evolution.user.core.request.UserCreateRequest;
import com.evolution.user.core.request.UserUpdateFirstNameRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserRequestTransform implements RequestTransform<String, UserRequestTypes, UserCommand> {

    private static final Logger logger = LoggerFactory.getLogger(UserRequestTransform.class);

    @Override
    public UserCommand transform(@Valid Request<String, UserRequestTypes> req) {
        logger.info("Request:" + req);

        UserRequestTypes type = req.getRequestType();

        switch (type) {
            case UserUpdateFirstNameRequest:
                UserUpdateFirstNameRequest userUpdateFirstNameRequest = (UserUpdateFirstNameRequest) req;
                return UserCommand.builder()
                        .key(userUpdateFirstNameRequest.getKey())
                        .correlation(MessageService.random())
                        .operationNumber(MessageService.random())
                        .content(UserContent.builder()
                                .firstName(userUpdateFirstNameRequest.getFirstName())
                                .build())
                        .build();
            case UserCreateRequest:
                UserCreateRequest userCreateRequest = (UserCreateRequest) req;
                String key = userCreateRequest.getKey();
                if (key == null) {
                    key = MessageService.random();
                    logger.info("UserCreateRequest, generated key:" + key);
                }

                return UserCommand.builder()
                        .key(key)
                        .correlation(MessageService.random())
                        .operationNumber(MessageService.random())
                        .type(type)
                        .content(UserContent.builder()
                                .username(userCreateRequest.getUsername())
                                .password(userCreateRequest.getPassword())
                                .firstName(userCreateRequest.getFirstName())
                                .lastName(userCreateRequest.getLastName())
                                .nickname(userCreateRequest.getNickname())
                                .build())
                        .build();
            default:
                throw new UnsupportedOperationException();
        }
    }


//    @Override
//    public UserCommand transform(@Valid Request<String, UserRequestTypes> request) {
//        UserRequestTypes type = request.getRequestType();
//        UserCommand command = UserCommand.builder()
//                .content(UserContent.builder().build())
//                .build();
//
//        String key = request.getKey();
//        if (key == null) {
//            key = MessageService.random();
//            logger.info("Request:" + request + ", key is null ! Generated random:" + key);
//        }
//
//        switch (type) {
//            case UserUpdateFirstNameRequest:
//                UserUpdateFirstNameRequest userUpdateFirstNameRequest = (UserUpdateFirstNameRequest) request;
//                command.getContent()
//                        .withFirstName(userUpdateFirstNameRequest.getFirstName());
//                break;
//            case UserCreateRequest:
//                UserCreateRequest userCreateRequest = (UserCreateRequest) request;
//                command.getContent()
//                        .withUsername(userCreateRequest.getUsername())
//                        .withPassword(userCreateRequest.getPassword())
//                        .withNickname(userCreateRequest.getNickname())
//                        .withFirstName(userCreateRequest.getFirstName())
//                        .withLastName(userCreateRequest.getLastName());
//                break;
//            default:
//                throw new UnsupportedOperationException();
//        }
//
//        command.withKey(key)
//                .withCorrelation(MessageService.random())
//                .withOperationNumber(MessageService.random())
//                .withType(type);
//
//        logger.info("Send command:" + command);
//
//        return command;
//    }

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
