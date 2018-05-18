package com.evolution.user.fake;

import com.evolution.library.core.v5.Message;
import com.evolution.library.core.v5.Request;
import com.evolution.user.core.UserCommand;
import com.evolution.user.core.UserRequestTransform;
import com.evolution.user.core.request.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class FakeData {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private UserRequestTransform userRequestTransform;

    private static final String key1 = UUID.randomUUID().toString().replace("-", "");
    private static final String key2 = "2";

    private static int count = 1;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        Request userCreateRequest1 = UserCreateRequest.builder()
                .key(key1)
                .username("maksim.lukaretskiy")
//                .username("maksim.lukaretskiy " + key1)
                .password("1234")
                .firstName("Maksim")
                .lastName("Lukaretskiy")
                .nickname("Infant")
                .build();

        UserCommand command1 = userRequestTransform.transform(userCreateRequest1);
        kafkaTemplate.send(command1.getFeed(), command1.getKey(), command1);
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
