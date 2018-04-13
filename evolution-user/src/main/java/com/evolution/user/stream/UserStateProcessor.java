package com.evolution.user.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Output;

public interface UserStateProcessor {

    String INPUT_CREATE = "input-create";

    @Output(INPUT_CREATE)
    KStream<?, ?> create();

    String INPUT_UPDATE = "input-update";

    @Output(INPUT_UPDATE)
    KStream<?, ?> update();

    String OUTPUT_RESULT = "output-result";

    @Output(OUTPUT_RESULT)
    KStream<?, ?> result();
}
