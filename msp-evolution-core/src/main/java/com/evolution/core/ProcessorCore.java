package com.evolution.core;

import com.evolution.core.event.Event;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.reactive.FluxSender;
import org.springframework.messaging.handler.annotation.SendTo;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@EnableBinding({Processor.class})
public class ProcessorCore {

    @StreamListener
    public void processor(@Input(Processor.INPUT) Flux<Event> input, @Output(Processor.OUTPUT) FluxSender output) {
        System.out.println("Processor");

//        List<Event> list = input.toStream()
//                .collect(Collectors.groupingBy(v -> v.getKey(), Collectors.summingLong(v -> v.getBalance())))
//                .entrySet()
//                .stream()
//                .map(v -> new Event(v.getKey(), v.getValue()))
//                .collect(Collectors.toList());
//        return Flux.fromStream(list.stream());

        List<Event> res = input.collect(Collectors.groupingBy(v -> v.getKey(), Collectors.summingLong(v -> v.getBalance())))
                .block()
                .entrySet()
                .stream()
                .map(v -> new Event(v.getKey(), v.getValue()))
                .collect(Collectors.toList());

        res.forEach(v -> System.out.println(v));

        output.send(Flux.fromStream(res.stream()));
    }

}
