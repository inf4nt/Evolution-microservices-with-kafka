package com.evolution.direct.message.processor;

import com.evolution.direct.message.processor.bindings.CommandProcessor;
import com.evolution.direct.message.processor.bindings.EventProcessor;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding({CommandProcessor.class, EventProcessor.class})
public class MessageProcessor {
}
