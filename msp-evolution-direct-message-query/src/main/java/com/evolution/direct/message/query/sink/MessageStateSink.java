package com.evolution.direct.message.query.sink;

import com.evolution.core.state.MessageDenormalizationState;
import com.evolution.direct.message.query.model.MessageModel;
import com.evolution.direct.message.query.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Optional;

@EnableBinding(Sink.class)
public class MessageStateSink {

    private static final Logger logger = LoggerFactory.getLogger(MessageStateSink.class);

    @Autowired
    private MessageRepository messageRepository;

    @StreamListener(Sink.INPUT)
    public void stateDenormalizationSink(MessageDenormalizationState state) {
        logger.info("Catch message denormalization state:" + state);

        MessageModel message = new MessageModel();
        Optional<MessageModel> or = messageRepository.findById(state.getKey());
        if (or.isPresent()) {
            message = or.get();
        }

        message.setKey(state.getKey());
        message.setText(state.getText());
        message.setEventNumber(state.getEventNumber());
        message.setSender(state.getSender());
        message.setRecipient(state.getRecipient());

        messageRepository.save(message);
    }
}
