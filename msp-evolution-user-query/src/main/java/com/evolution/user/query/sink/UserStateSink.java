package com.evolution.user.query.sink;

import com.evolution.core.UserState;
import com.evolution.user.query.model.UserModel;
import com.evolution.user.query.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import javax.validation.Valid;
import java.util.Optional;

@EnableBinding(Sink.class)
public class UserStateSink {

    private static final Logger logger = LoggerFactory.getLogger(UserStateSink.class);

    private final UserRepository userRepository;

    @Autowired
    public UserStateSink(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @StreamListener(Sink.INPUT)
    public void state(@Valid UserState state) {
        logger.info("Catch UserState:" + state);
        Optional<UserModel> original = userRepository.findByUsername(state.getUsername());
        UserModel user = new UserModel();
        if (!original.isPresent()) {
            user.setKey(state.getKey());
        }

        user.setUsername(state.getUsername());
        user.setPassword(state.getPassword());
        user.setFirstName(state.getFirstName());
        user.setLastName(state.getLastName());
        user.setEventNumber(state.getEventNumber());

        userRepository.save(user);
    }
}
