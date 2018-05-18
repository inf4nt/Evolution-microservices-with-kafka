package com.evolution.core;

import com.evolution.core.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Fake {

    private final String key = "maksim.lukaretskiy";

    private Long count = 0L;

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void init() {
        Event event = new Event();
        event.setKey(key);
        event.setBalance(count++);

        kafkaTemplate.send("EventFeed", event.getKey(), event);

        System.out.println("Send event:" + event);
    }
}
