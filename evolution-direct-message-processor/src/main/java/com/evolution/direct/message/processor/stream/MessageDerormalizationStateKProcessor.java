//package com.evolution.direct.message.processor.stream;
//
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//
//public interface MessageDerormalizationStateKProcessor {
//
//    String INPUT_MESSAGE_STATE = "input-message-state";
//
//    @Input(INPUT_MESSAGE_STATE)
//    KStream<?, ?> inputMessageState();
//
//    String INPUT_USER_STATE = "input-user-state";
//
//    @Input(INPUT_USER_STATE)
//    KStream<?, ?> inputUserState();
//
//    String OUTPUT_MESSAGE_DENORMALIZATION_STATE = "output-message-denormalization-state";
//
//    @Output(OUTPUT_MESSAGE_DENORMALIZATION_STATE)
//    KStream<?, ?> outputDenormalizationState();
//}
