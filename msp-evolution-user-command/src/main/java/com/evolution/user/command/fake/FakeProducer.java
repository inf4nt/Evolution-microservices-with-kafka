package com.evolution.user.command.fake;

import com.evolution.core.command.UserCreateCommand;
import com.evolution.core.command.UserUpdateFirstNameLastNameCommand;
import com.evolution.library.core.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
public class FakeProducer {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

    private String key1 = "11111";
    private String key2 = "22222";
    private String key3 = "33333";


    @PostConstruct
    public void create() {
        UserCreateCommand c1 = UserCreateCommand.builder()
                .key(key1)
                .operationNumber(UUID.randomUUID().toString())
                .username("maksim.lukaretskiy " + key1)
                .password("1234")
                .firstName("maksim")
                .lastName("lukaretskiy")
                .build();

        kafkaTemplate.send(c1.getFeed(), c1.getKey(), c1);

        UserCreateCommand c2 = UserCreateCommand.builder()
                .key(key2)
                .operationNumber(UUID.randomUUID().toString())
                .username("yevgen.berberyan " + key2)
                .password("1111")
                .firstName("yevgen")
                .lastName("berberyan")
                .build();

        kafkaTemplate.send(c2.getFeed(), c2.getKey(), c2);

        UserCreateCommand c3 = UserCreateCommand.builder()
                .key(key3)
                .operationNumber(UUID.randomUUID().toString())
                .username("michail.melnichuk " + key3)
                .password("aaaa")
                .firstName("michail")
                .lastName("melnichuk")
                .build();

        kafkaTemplate.send(c3.getFeed(), c3.getKey(), c3);
    }

//    @Scheduled(fixedDelay = 10000)
//    public void update() {
//        UserUpdateFirstNameLastNameCommand c1 = UserUpdateFirstNameLastNameCommand.builder()
//                .key(key1)
//                .operationNumber(UUID.randomUUID().toString())
//                .firstName("maksim " + new Date())
//                .lastName("lukaretskiy " + new Date())
//                .build();
//        kafkaTemplate.send(c1.getFeed(), c1.getKey(), c1);
//
//        UserUpdateFirstNameLastNameCommand c2 = UserUpdateFirstNameLastNameCommand.builder()
//                .key(key2)
//                .operationNumber(UUID.randomUUID().toString())
//                .firstName("yevgen "+ new Date())
//                .lastName("berberyan "+ new Date())
//                .build();
//        kafkaTemplate.send(c2.getFeed(), c2.getKey(), c2);
//
//        UserUpdateFirstNameLastNameCommand c3 = UserUpdateFirstNameLastNameCommand.builder()
//                .key(key3)
//                .operationNumber(UUID.randomUUID().toString())
//                .firstName("michail" + new Date())
//                .lastName("melnichuk" + new Date())
//                .build();
//        kafkaTemplate.send(c3.getFeed(), c3.getKey(), c3);
//    }
}
