package com.evolution.user.fake;

import com.evolution.library.core.v3.MessageService;
import com.evolution.library.core.v3.global.GlobalMessage;
import com.evolution.user.core.basic.UserCreateRequestCommand;
import com.evolution.user.core.global.UserGlobalCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class FakeProducer {

    @Autowired
    private KafkaTemplate<String, GlobalMessage<String>> kafkaTemplate;

    private final static String key = UUID.randomUUID().toString().replace("-", "");

    private static int count = 1;

    @PostConstruct
    public void init() {
        // todo create emulate reqest command
        UserCreateRequestCommand createRequestCommand = UserCreateRequestCommand.builder()
                .key(key)
                .username("evolution.username")
                .password("1234")
                .firstName("some first name")
                .lastName("some last name")
                .build();

        // todo convert request command to abstract command
        // todo create implementation for auto generate abstract command

        UserGlobalCommand command = UserGlobalCommand.builder()
                .key(createRequestCommand.getKey())
                .commandNumber(MessageService.random())
                .messageType(UserCreateRequestCommand.class.getSimpleName())
                .correlation(MessageService.random())
                .username(createRequestCommand.getUsername())
                .password(createRequestCommand.getPassword())
                .firstName(createRequestCommand.getFirstName())
                .lastName(createRequestCommand.getLastName())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }

//    @Scheduled(fixedDelay = 60000)
//    public void process() {
//
//        UserUpdateFirstNameRequestCommand requestCommand = UserUpdateFirstNameRequestCommand
//                .builder()
//                .key(key)
//                .firstName("first name " + count)
//                .build();
//
//        UserGlobalCommand command = UserGlobalCommand.builder()
//                .key(requestCommand.getKey())
//                .commandNumber(MessageService.random())
//                .correlation(MessageService.random())
//                .messageType(UserUpdateFirstNameRequestCommand.class.getSimpleName())
//                .firstName(requestCommand.getFirstName())
//                .build();
//
//        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
//
//        count++;
//    }
}
