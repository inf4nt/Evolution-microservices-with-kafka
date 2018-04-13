package com.evolution.user.stream;

import com.evolution.user.event.UserStateEvent;
import com.evolution.user.layer.query.model.UserState;
import com.evolution.user.layer.query.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Optional;

@EnableBinding(UserStateSink.class)
public class UserStateSinkStream {

    @Autowired
    private UserRepository userRepository;

    @StreamListener(UserStateSink.INPUT_RESULT_STATE)
    public void processState(UserStateEvent event) {
        Optional<UserState> original = userRepository.findById(event.getId());
        UserState us = new UserState();
        if (original.isPresent()) {
            // update state
            us = original.get();
        }

        us.setEventId(event.getEventId());
        us.setUsername(event.getUsername());
        us.setPassword(event.getPassword());
        us.setFirstName(event.getFirstName());
        us.setLastName(event.getLastName());
        us.setNickname(event.getNickname());
        us.setDatePost(event.getDatePost());
        us.setDatePut(event.getDatePut());

        userRepository.save(us);
    }
}
