package evolution.fake;

import evolution.core.Base;
import evolution.command.UserCreateCommand;
import evolution.command.UserUpdateUsernameCommand;
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

    private static final String key2 = UUID.randomUUID().toString();

    private static final String key3 = UUID.randomUUID().toString();

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


        UserCreateCommand command2 = UserCreateCommand
                .builder()
                .key(key2)
                .username("mail" + new Date())
                .password("1234")
                .firstName("fn")
                .lastName("ln")
                .build();

        UserCreateCommand command3 = UserCreateCommand
                .builder()
                .key(key3)
                .username("mail" + new Date())
                .password("1234")
                .firstName("fn")
                .lastName("ln")
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
                .username("com.infant@gmail.com" + new Date())
                .build();

        kafkaTemplate.send(command.getFeed(), command.getKey(), command);

        UserUpdateUsernameCommand command2 = UserUpdateUsernameCommand
                .builder()
                .key(key2)
                .username("username" + new Date())
                .build();

        kafkaTemplate.send(command2.getFeed(), command2.getKey(), command2);

        UserUpdateUsernameCommand command3 = UserUpdateUsernameCommand
                .builder()
                .key(key3)
                .username("username" + new Date())
                .build();

        kafkaTemplate.send(command3.getFeed(), command3.getKey(), command3);
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
