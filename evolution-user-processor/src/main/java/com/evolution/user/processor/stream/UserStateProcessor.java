package com.evolution.user.processor.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface UserStateProcessor {

    String INPUT_CREATE_USER = "input-create-user";

    @Input(INPUT_CREATE_USER)
    KStream<?, ?> create();

//    String INPUT_UPDATE = "input-update-user";
//
//    @Input(INPUT_UPDATE)
//    KStream<?, ?> update();

    String OUTPUT_STATE_USER = "output-state-user";

    @Output(OUTPUT_STATE_USER)
    KStream<?, ?> result();
}
