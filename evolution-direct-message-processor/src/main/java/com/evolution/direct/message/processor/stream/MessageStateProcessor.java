package com.evolution.direct.message.processor.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface MessageStateProcessor {

    String INPUT_CREATE = "input-create";

    @Input(INPUT_CREATE)
    KStream<?, ?> create();

    String INPUT_UPDATE = "input-update";

    @Input(INPUT_UPDATE)
    KStream<?, ?> update();

    String OUTPUT_RESULT = "output-result";

    @Output(OUTPUT_RESULT)
    KStream<?, ?> result();
}
