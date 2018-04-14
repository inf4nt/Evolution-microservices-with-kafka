package com.evolution.direct.message.stream.processor.stream;

import com.evolution.direct.message.stream.processor.event.CreateMessageEvent;
import com.evolution.direct.message.stream.processor.event.UpdateTextMessageEvent;
import com.evolution.direct.message.stream.processor.event.UserStateEvent;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(MessageProcessor.class)
public class MessageProcessorStream {

    @StreamListener
    public void process(@Input(MessageProcessor.INPUT_CREATE_MESSAGE) KStream<String, CreateMessageEvent> createMessageEventKStream,
                        @Input(MessageProcessor.INPUT_UPDATE_TEXT_MESSAGE) KStream<String, UpdateTextMessageEvent> updateTextMessageEventKStream,
                        @Input(MessageProcessor.INPUT_USER_STATE) KStream<String, UserStateEvent> userStateEventKStream) {
        System.out.println("PROCESSOR, PROCESSOR");
    }
}
