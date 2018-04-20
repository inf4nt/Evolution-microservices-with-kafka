package com.evolution.user.stream;

import com.evolution.user.event.UserStateEvent;
import com.evolution.user.layer.query.model.UserState;
import com.evolution.user.layer.query.repository.UserRepository;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Optional;

@EnableBinding(Sink.class)
public class UserStateSinkStream {

    private static final Logger logger = LoggerFactory.getLogger(UserStateSinkStream.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QueryableStoreRegistry queryableStoreRegistry;

    @StreamListener(Sink.INPUT)
    public void processState(UserStateEvent event) {
        logger.info("Catch event state:" + event);

        Optional<UserState> original = userRepository.findById(event.getId());
        UserState us = new UserState();
        if (original.isPresent()) {
            // update state
            us = original.get();
        }

        us.setId(event.getId());
        us.setEventId(event.getEventId());
        us.setUsername(event.getUsername());
        us.setPassword(event.getPassword());
        us.setFirstName(event.getFirstName());
        us.setLastName(event.getLastName());
        us.setNickname(event.getNickname());
        us.setDatePost(event.getDatePost());
        us.setDatePut(event.getDatePut());

        logger.info("Write state:" + us);

        userRepository.save(us);

        try {
            final ReadOnlyKeyValueStore<String, UserStateEvent> topFiveStore =
                    queryableStoreRegistry.getQueryableStoreType("UserStateEventStore", QueryableStoreTypes.<String, UserStateEvent>keyValueStore());
            UserStateEvent s = topFiveStore.get(event.getId());
            System.out.println("STORE:" + s);
        } catch (Exception e) {

        }

        try {

            final ReadOnlyKeyValueStore<String, UserStateEvent> topFiveStore2 =
                    queryableStoreRegistry.getQueryableStoreType("UserStateEventStore2", QueryableStoreTypes.<String, UserStateEvent>keyValueStore());
            UserStateEvent s2 = topFiveStore2.get(event.getId());
            System.out.println("STORE2:" + s2);
        } catch (Exception e) {

        }



    }
}
