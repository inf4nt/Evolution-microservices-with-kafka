package com.evolution.user.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface UserStateSink {

    String INPUT_RESULT_STATE = "input-result-state";

    @Output(INPUT_RESULT_STATE)
    SubscribableChannel input();
}
