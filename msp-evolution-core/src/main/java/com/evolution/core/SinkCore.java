//package com.evolution.core;
//
//import com.evolution.core.event.Event;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@EnableBinding(Sink.class)
//public class SinkCore {
//
//    @StreamListener
//    public void sink(@Input(Sink.INPUT)Flux<Event> input) {
//        System.out.println("Processor");
//        List<Event> list = input.toStream()
//                .collect(Collectors.groupingBy(v -> v.getKey(), Collectors.summingLong(v -> v.getBalance())))
//                .entrySet()
//                .stream()
//                .map(v -> new Event(v.getKey(), v.getValue()))
//                .collect(Collectors.toList());
//        list.forEach(v -> System.out.println(v));
//    }
//}
