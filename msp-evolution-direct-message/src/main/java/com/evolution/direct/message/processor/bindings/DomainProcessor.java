package com.evolution.direct.message.processor.bindings;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface DomainProcessor {

    String INPUT = "domain-input";

    @Input(INPUT)
    KTable<?, ?> input();

    String OUTPUT = "domain-output";

    @Output(OUTPUT)
    KStream<?, ?> output();

    String INPUT_USER_DOMAIN = "user-domain-input";

    @Input(INPUT_USER_DOMAIN)
    KTable<?, ?> inputUserDomain();
}
