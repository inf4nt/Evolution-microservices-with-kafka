package com.evolution.msp.user.fake;

import com.evolution.msp.user.core.Base;
import com.evolution.msp.user.write.command.UserCreateCommand;
import com.evolution.msp.user.write.command.UserUpdateUsernameCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Service
public class FakeDataProducer {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

    private static final String key = UUID.randomUUID().toString();

    @PostConstruct
    public void init() {
        UserCreateCommand command = UserCreateCommand
                .builder()
                .key(key)
                .username("com.infant@gmail.com" + new Date())
                .password("1234")
                .firstName("Maksim")
                .lastName("Lukaretskiy")
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }


    @Scheduled(fixedDelay = 5000)
    public void produce() {
        UserUpdateUsernameCommand command = UserUpdateUsernameCommand
                .builder()
                .key(key)
                .username("com.infant@gmail.com" + new Date())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
    }


//    @Scheduled(fixedDelay = 5000)
//    public void produce() {
//        UserBalanceChangeEvent command = UserBalanceChangeEvent
//                .builder()
//                .key(UUID.randomUUID().toString())
//                .username("com.infant@gmail.com")
//                .balance(100L)
//                .build();
//
//        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
//    }
//
//    @Scheduled(fixedDelay = 2000)
//    public void produce2() {
//        UserBalanceChangeEvent command = UserBalanceChangeEvent
//                .builder()
//                .key(UUID.randomUUID().toString())
//                .username("com.infant@gmail.com")
//                .balance(-30L)
//                .build();
//
//        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
//    }
}
