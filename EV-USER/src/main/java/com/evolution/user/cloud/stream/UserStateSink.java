package com.evolution.user.cloud.stream;

import com.evolution.user.core.state.UserState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class UserStateSink {

    private static final Logger logger = LoggerFactory.getLogger(UserStateSink.class);

    @StreamListener(Sink.INPUT)
    public void setState(UserState state) {
        logger.info("Catch user state:" + state);
    }
}
