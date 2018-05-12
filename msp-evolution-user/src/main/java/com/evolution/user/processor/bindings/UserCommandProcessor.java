package com.evolution.user.processor.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserCommandProcessor {

    String INPUT = "command-input";

    @Input(INPUT)
    SubscribableChannel input();

    String OUTPUT = "command-output";

    @Output(OUTPUT)
    MessageChannel output();
}
