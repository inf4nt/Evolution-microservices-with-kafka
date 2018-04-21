package com.evolution.user.fake;

import com.evolution.user.core.base.Base;
import com.evolution.user.core.command.UserCreateCommand;
import com.evolution.user.core.command.UserUpdateUsernameCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Component
public class FakeUserProducer {

    @Autowired
    private KafkaTemplate<String, Base<String>> kafkaTemplate;

    private static final String key = UUID.randomUUID().toString();

    private static final String key2 = UUID.randomUUID().toString();

    private static final String key3 = UUID.randomUUID().toString();

    @PostConstruct
    public void init() {
        UserCreateCommand command = UserCreateCommand
                .builder()
                .key(key)
                .username("Maksim.Lukaretskiy" + new Date())
                .password("1234")
                .firstName("Maksim")
                .lastName("Lukaretskiy")
                .build();


        UserCreateCommand command2 = UserCreateCommand
                .builder()
                .key(key2)
                .username("Yevgen.Berberyan" + new Date())
                .password("1234")
                .firstName("Yevgen")
                .lastName("Berberyan")
                .build();

        UserCreateCommand command3 = UserCreateCommand
                .builder()
                .key(key3)
                .username("Mihail.Melnichuk" + new Date())
                .password("1234")
                .firstName("Mihail")
                .lastName("Mihail")
                .build();


        kafkaTemplate.send(command.getFeed(), command.getKey(), command);
        kafkaTemplate.send(command2.getFeed(), command2.getKey(), command2);
        kafkaTemplate.send(command3.getFeed(), command3.getKey(), command3);
    }


    @Scheduled(fixedDelay = 5000)
    public void produce() {
        UserUpdateUsernameCommand command = UserUpdateUsernameCommand
                .builder()
                .key(key)
                .username("lukaretskiy" + new Date())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);

        UserUpdateUsernameCommand command2 = UserUpdateUsernameCommand
                .builder()
                .key(key2)
                .username("berberyan" + new Date())
                .build();

        kafkaTemplate.send(command2.getFeed(), command2.getKey(), command2);

        UserUpdateUsernameCommand command3 = UserUpdateUsernameCommand
                .builder()
                .key(key3)
                .username("melnichuk" + new Date())
                .build();

        kafkaTemplate.send(command3.getFeed(), command3.getKey(), command3);
    }
}
