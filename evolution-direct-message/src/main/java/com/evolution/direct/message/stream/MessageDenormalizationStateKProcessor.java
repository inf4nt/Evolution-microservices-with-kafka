//package com.evolution.direct.message.stream;
//
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//
//public interface MessageDenormalizationStateKProcessor {
//
////    String INPUT_MESSAGE_STATE = "input-message-state";
////
////    @Input(INPUT_MESSAGE_STATE)
////    KStream<?, ?> inputMessageState();
//
//
//
//    String INPUT_MESSAGE_CREATE = "input-message-create";
//
//    @Input(INPUT_MESSAGE_CREATE)
//    KStream<?, ?> inputMessageCreeate();
//
//    String INPUT_MESSAGE_UPDATE_TEXT = "input-message-update-text";
//
//    @Input(INPUT_MESSAGE_UPDATE_TEXT)
//    KStream<?, ?> inputMessageUpdateText();
//
//    String INPUT_USER_STATE = "input-user-state";
//
//    @Input(INPUT_USER_STATE)
//    KStream<?, ?> inputUserState();
//
//    String OUTPUT_MESSAGE_DENORMALIZATION_STATE = "output-message-denormalization-state";
//
//    @Output(OUTPUT_MESSAGE_DENORMALIZATION_STATE)
//    KStream<?, ?> outputMessageDenormalizationState();
//}
