package com.evolution.core.processor.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface UserEventAggregatorProcessor {

    String INPUT = "user-event-aggregator-input";

    @Input(INPUT)
    KStream<?, ?> eventInput();

    String OUTPUT = "user-state-output";

    @Output(OUTPUT)
    KStream<?, ?> stateOutput();
}
