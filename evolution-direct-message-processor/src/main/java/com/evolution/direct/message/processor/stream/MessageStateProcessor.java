package com.evolution.direct.message.processor.stream;

import com.evolution.direct.message.processor.event.MessageCreateEvent;
import com.evolution.direct.message.processor.event.MessageDenormalizationStateEvent;
import com.evolution.direct.message.processor.event.UserStateEvent;
import com.evolution.direct.message.processor.service.MessageEventBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.messaging.handler.annotation.SendTo;

import static com.evolution.direct.message.processor.stream.MessageStateKProcessor.*;

@EnableBinding({MessageStateKProcessor.class})
public class MessageStateProcessor {

    @Autowired
    private ObjectMapper objectMapper;

//    @StreamListener
//    @SendTo(OUTPUT_MESSAGE_STATE)
//    public KStream<String, UserStateEvent> processState(@Input(INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> createStream,
//                                                            @Input(INPUT_USER_STATE) KStream<String, UserStateEvent> userStateStream) {
//        return userStateStream;
//    }

    @StreamListener
    @SendTo(OUTPUT_MESSAGE_STATE)
    public KStream<String, MessageDenormalizationStateEvent> processState(@Input(INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> createStream,
                                                                          @Input(INPUT_USER_STATE) KStream<String, UserStateEvent> userStateStream) {

        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);

        return createStream
                .join(userStateStream, (r, l) -> MessageEventBuilder.buildForSender(r, l), JoinWindows.of(999), Serdes.String(), serdeCreate, serdeUserState);
    }

//    @StreamListener
//    @SendTo(OUTPUT_MESSAGE_STATE)
//    public KStream<String, MessageDenormalizationStateEvent> processState(@Input(INPUT_MESSAGE_CREATE) KStream<String, MessageCreateEvent> createStream,
//                                                                          @Input(INPUT_MESSAGE_UPDATE_TEXT) KStream<String, MessageUpdateTextEvent> updateTextStream,
//                                                                          @Input(INPUT_USER_STATE) KStream<String, UserStateEvent> userStateStream) {
//
//        Serde<MessageCreateEvent> serdeCreate = new JsonSerde<>(MessageCreateEvent.class, objectMapper);
//        Serde<MessageUpdateTextEvent> serdeUpdateText = new JsonSerde<>(MessageUpdateTextEvent.class, objectMapper);
//
//        Serde<MessageStateEvent> serdeMessageState = new JsonSerde<>(MessageStateEvent.class, objectMapper);
//        Serde<UserStateEvent> serdeUserState = new JsonSerde<>(UserStateEvent.class, objectMapper);
//
//        KStream<String, MessageStateEvent> state = createStream.leftJoin(updateTextStream, (c, u) -> MessageEventBuilder.buildState(c, u), JoinWindows.of(999),
//                Serdes.String(),
//                serdeCreate,
//                serdeUpdateText
//        );
//
//        KStream<String, MessageDenormalizationStateEvent> senderState = state
//                .map((k, v) -> new KeyValue<>(v.getSender(), MessageEventBuilder.buildForMap(v)))
//                .join(userStateStream, (r, l) -> MessageEventBuilder.buildForSender(r, l), JoinWindows.of(999), Serdes.String(), serdeMessageState, serdeUserState)
//                .map((k, v) -> new KeyValue<>(v.getSuperId(), v));
//
//        return senderState;
//    }
}
