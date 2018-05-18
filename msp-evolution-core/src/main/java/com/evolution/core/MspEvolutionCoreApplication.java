package com.evolution.core;

import com.evolution.core.event.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class MspEvolutionCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MspEvolutionCoreApplication.class, args);
    }
}












//public class MspEvolutionCoreApplication {
//
//    public static void main(String[] args) {
////        List<Event> list = new ArrayList<>(Arrays.asList(new Event("maksim", 1L), new Event("maksim", 1L), new Event("maksim", 3L)));
////        Flux<Event> eventFlux = Flux.fromIterable(list);
////        Mono<Event> m = eventFlux.reduce((event, event2) -> new Event(event.getKey(), event.getBalance() + event2.getBalance()));
////        Event e = m.block();
////        System.out.println(e);
//
//
////        List<Event> list = new ArrayList<>(Arrays.asList(new Event("michail", 12L),
////                new Event("maksim", 1L), new Event("yevgen", 10L), new Event("maksim", 3L)));
////        Flux<Event> eventFlux = Flux.fromIterable(list);
////
////        List<Event> res = eventFlux.toStream()
////                .collect(Collectors.groupingBy(v -> v.getKey(), Collectors.summingLong(v -> v.getBalance())))
////                .entrySet()
////                .stream()
////                .map(v -> new Event(v.getKey(), v.getValue()))
////                .collect(Collectors.toList());
////        Flux<Event> fr = Flux.fromStream(res.stream());
////        fr.toStream().forEach(v -> System.out.println(v));
//
//
//
//
//
//        List<Event> list = new ArrayList<>(Arrays.asList(new Event("michail", 12L),
//                new Event("maksim", 1L), new Event("yevgen", 10L), new Event("maksim", 3L)));
//        Flux<Event> eventFlux = Flux.fromIterable(list);
//
//        eventFlux.collect(Collectors.groupingBy(v -> v.getKey(), Collectors.summingLong(v -> v.getBalance())))
//                .block().entrySet()
//                .stream()
//                .forEach(v -> System.out.println(v));
//
//
////                .toStream().forEach(v -> System.out.println(v));
//
//    }
//}