package com.evolution.user.base.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CommandSink {

    String INPUT_COMMAND_STATUS = "input-command-status";

    @Input(INPUT_COMMAND_STATUS)
    SubscribableChannel inputStatus();
}
