package com.evolution.user.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface UserStateProcessor {

    String INPUT_CREATE_USER = "input-create-user";

    String INPUT_UPDATE_USER = "input-update-user";

    String OUTPUT_STATE_USER = "output-state-user";

    @Input(INPUT_CREATE_USER)
    KStream<?, ?> createUser();

    @Input(INPUT_UPDATE_USER)
    KStream<?, ?> updateUser();

    @Output(OUTPUT_STATE_USER)
    KStream<?, ?> outputStateUser();
}
