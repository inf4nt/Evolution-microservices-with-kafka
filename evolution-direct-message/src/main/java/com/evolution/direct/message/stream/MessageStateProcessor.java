//package com.evolution.direct.message.stream;
//
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//
//public interface MessageStateProcessor {
//
//    String INPUT_STATE_USER = "input-state-user";
//
//    @Input(INPUT_STATE_USER)
//    KStream<?, ?> inputUserState();
//
//    String INPUT_CREATE_MESSAGE = "input-create-message";
//
//    String INPUT_UPDATE_TEXT_MESSAGE = "input-update-text-message";
//
//    String OUTPUT_STATE_MESSAGE = "output-state-message";
//
//    @Input(INPUT_CREATE_MESSAGE)
//    KStream<?, ?> createMessage();
//
//    @Input(INPUT_UPDATE_TEXT_MESSAGE)
//    KStream<?, ?> updateTextMessage();
//
//    @Output(OUTPUT_STATE_MESSAGE)
//    KStream<?, ?> outputStateMessage();
//}
