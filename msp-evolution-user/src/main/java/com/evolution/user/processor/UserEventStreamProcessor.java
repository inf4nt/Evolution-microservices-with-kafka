package com.evolution.user.processor;

import com.evolution.library.core.v3.MessageService;
import com.evolution.user.core.basic.UserCreateRequestCommand;
import com.evolution.user.core.basic.UserUpdateFirstNameRequestCommand;
import com.evolution.user.core.global.UserGlobalEvent;
import com.evolution.user.core.global.UserGlobalState;
import com.evolution.user.processor.bindings.UserEventProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(UserEventProcessor.class)
public class UserEventStreamProcessor {

    @Autowired
    private ObjectMapper objectMapper;

    @StreamListener(UserEventProcessor.INPUT)
    @SendTo(UserEventProcessor.OUTPUT)
    public KStream<String, UserGlobalState> processState(KStream<String, UserGlobalEvent> input) {
        System.out.println("processState:");

        final Serde<UserGlobalEvent> eventSerde = new JsonSerde<>(UserGlobalEvent.class, objectMapper);
        final Serde<UserGlobalState> stateEventSerde = new JsonSerde<>(UserGlobalState.class, objectMapper);

        return input
                .groupByKey(Serialized.with(Serdes.String(), eventSerde))
                .aggregate(UserGlobalState::new, (key, event, state) -> handle(event, state), Materialized.with(Serdes.String(), stateEventSerde))
                .toStream();
    }

    public UserGlobalState handle(UserGlobalEvent event, UserGlobalState state) {
        String type = event.getMessageType();

        if (type.equals(MessageService.getMessageType(UserCreateRequestCommand.class))) {
            return UserGlobalState
                    .builder()
                    .messageType(event.getMessageType())
                    .key(event.getKey())
                    .eventNumber(event.getEventNumber())
                    .correlation(MessageService.random())

                    .username(event.getUsername())
                    .password(event.getPassword())
                    .firstName(event.getFirstName())
                    .lastName(event.getLastName())

                    .build();
        } else if (type.equals(MessageService.getMessageType(UserUpdateFirstNameRequestCommand.class))) {
            return UserGlobalState
                    .builder()
                    .firstName(event.getFirstName())

                    .messageType(event.getMessageType())
                    .key(state.getKey())
                    .eventNumber(state.getEventNumber())
                    .correlation(MessageService.random())
                    .username(state.getUsername())
                    .password(state.getPassword())
                    .lastName(state.getLastName())
                    .build();
        } else {
            throw new UnsupportedOperationException("UserGlobalState handle");
        }
    }
}
