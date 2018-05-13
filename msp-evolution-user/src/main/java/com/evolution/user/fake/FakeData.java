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

    private static final String key1 = "1";
    private static final String key2 = "2";

    private static int count = 1;

    @PostConstruct
    public void init() {
        UserCreateRequest userCreateRequest1 = UserCreateRequest.builder()
                .key(key1)
                .username("maksim.lukaretskiy" + key1)
                .password("1234")
                .firstName("Maksim")
                .lastName("Lukaretskiy")
                .build();

        UserCommand command1 = userRequestTransform.transform(userCreateRequest1);

        kafkaTemplate.send(command1.getFeed(), command1.getKey(), command1);


        UserCreateRequest userCreateRequest2 = UserCreateRequest.builder()
                .key(key1)
                .username("yevgen.berberyan" + key2)
                .password("1234")
                .firstName("Yevgen")
                .lastName("Berberyan")
                .build();

        UserCommand command2 = userRequestTransform.transform(userCreateRequest2);

        kafkaTemplate.send(command2.getFeed(), command2.getKey(), command2);
    }

//    @Scheduled(fixedDelay = 5000)
//    public void scheduler() {
//        UserUpdateFirstNameRequest request = UserUpdateFirstNameRequest.builder()
//                .key(key)
//                .firstName("first name " + count)
//                .build();
//
//
//        UserCommand command = userRequestTransform.transform(request);
//
//        kafkaTemplate.send(command.getFeed(), command.getFeed(), command);
//
//        count++;
//    }

}
