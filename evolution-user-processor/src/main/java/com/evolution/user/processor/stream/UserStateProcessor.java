package com.evolution.user.processor.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface UserStateProcessor {

    String INPUT_CREATE = "input-create";

    @Input(INPUT_CREATE)
    KStream<?, ?> create();

    String INPUT_UPDATE = "input-update";

    @Input(INPUT_UPDATE)
    KStream<?, ?> update();

    String OUTPUT_RESULT = "output-result-state";

    @Output(OUTPUT_RESULT)
    KStream<?, ?> result();
}
