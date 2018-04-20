package com.evolution.direct.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageSink {
    String INPUT_MESSAGE_STATE = "input-message-state";

    @Input(INPUT_MESSAGE_STATE)
    SubscribableChannel ims();

    String INPUT_MESSAGE_STATE_DENORMALIZATE = "input-message-state-denormalizate";

    @Input(INPUT_MESSAGE_STATE_DENORMALIZATE)
    SubscribableChannel imsd();
}
