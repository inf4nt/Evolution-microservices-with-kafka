//package com.evolution.user.processor.bindings;
//
//import org.apache.kafka.streams.kstream.KStream;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.Output;
//
//public interface DomainProcessor {
//
//    String INPUT = "domain-input";
//
//    @Input(INPUT)
//    KStream<?, ?> input();
//
//    String OUTPUT = "domain-output";
//
//    @Output(OUTPUT)
//    KStream<?, ?> output();
//}
