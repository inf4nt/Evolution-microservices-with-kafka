package com.evolution.direct.message.processor.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface EventProcessor {

    String INPUT = "message-event-input";

    @Input(INPUT)
    KStream<?, ?> input();

    String OUTPUT = "message-event-output";

    @Output(OUTPUT)
    KStream<?, ?> output();
}
