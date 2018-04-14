package com.evolution.direct.message.stream.processor.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface MessageProcessor {

    String INPUT_CREATE_MESSAGE = "input-create-message";

    @Input(INPUT_CREATE_MESSAGE)
    KStream<?, ?> inputCreateMessage();

    String INPUT_UPDATE_TEXT_MESSAGE = "input-update-text-message";

    @Input(INPUT_UPDATE_TEXT_MESSAGE)
    KStream<?, ?> inputUpdateMessage();

//    String OUTPUT_STATE_MESSAGE = "output-state-message";
//
//    @Output(OUTPUT_STATE_MESSAGE)
//    KStream<?, ?> outputStateMessage();



    String INPUT_USER_STATE = "input-user-state";

    @Input(INPUT_USER_STATE)
    KStream<?, ?> inputUserState();
}
