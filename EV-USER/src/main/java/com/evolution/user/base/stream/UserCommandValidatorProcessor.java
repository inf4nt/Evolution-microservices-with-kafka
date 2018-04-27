package com.evolution.user.base.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserCommandValidatorProcessor {

    String INPUT = "input-user-create-command";

    @Input(INPUT)
    SubscribableChannel input();

    String OUTPUT = "output-user-command-validation";

    @Output(OUTPUT)
    MessageChannel output();
}
