package com.evolution.user.stream;

import com.evolution.user.event.UserCreateEvent;
import com.evolution.user.event.UserStateEvent;
import com.evolution.user.event.UserUpdateEvent;
import com.evolution.user.layer.query.repository.UserRepository;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(UserStateProcessor.class)
public class UserStateProcessorStream {

    @Autowired
    private UserRepository userRepository;

    @StreamListener
    @SendTo(UserStateProcessor.OUTPUT_RESULT)
    public KStream<String, UserStateEvent> processState(@Input(UserStateProcessor.INPUT_CREATE) KStream<String, UserCreateEvent> createStream,
                                                        @Input(UserStateProcessor.INPUT_UPDATE) KStream<String, UserUpdateEvent> updateStream) {



        return null;
    }

}
