package com.evolution.direct.message.processor.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface MessageStateKProcessor {

    String INPUT_MESSAGE_CREATE = "input-message-create";

    @Input(INPUT_MESSAGE_CREATE)
    KStream<?, ?> inputMessageCreate();

    String INPUT_MESSAGE_UPDATE_TEXT = "input-message-update-text";

    @Input(INPUT_MESSAGE_UPDATE_TEXT)
    KStream<?, ?> inputMessageUpdateText();

    String OUTPUT_MESSAGE_STATE = "output-message-state";

    @Output(OUTPUT_MESSAGE_STATE)
    KStream<?, ?> outputStateMessage();
}
