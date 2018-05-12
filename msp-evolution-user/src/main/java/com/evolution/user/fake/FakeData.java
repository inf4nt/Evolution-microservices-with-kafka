package com.evolution.user.fake;

import com.evolution.library.core.v4.Message;
import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.UserCommand;
import com.evolution.user.core.UserRequestTransform;
import com.evolution.user.core.request.UserCreateRequest;
import com.evolution.user.core.request.UserUpdateFirstNameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FakeData {

    @Autowired
    private KafkaTemplate<String, Message<String>> kafkaTemplate;

    @Autowired
    private UserRequestTransform userRequestTransform;

    private static final String key = MessageService.random();

    private static int count = 1;

    @PostConstruct
    public void init() {
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .key(key)
                .username("maksim.lukaretskiy" + key)
                .password("1234")
                .firstName("Maksim")
                .lastName("Lukaretskiy")
                .build();

        UserCommand command = userRequestTransform.transform(userCreateRequest);

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);

    }

    @Scheduled(fixedDelay = 10000)
    public void scheduler() {
        UserUpdateFirstNameRequest request = UserUpdateFirstNameRequest.builder()
                .key(key)
                .firstName("first name " + count)
                .build();


        UserCommand command = userRequestTransform.transform(request);

        kafkaTemplate.send(command.getFeed(), command.getFeed(), command);

        count++;
    }

}
