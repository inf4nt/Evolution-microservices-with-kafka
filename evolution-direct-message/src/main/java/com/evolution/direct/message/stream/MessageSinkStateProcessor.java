package com.evolution.direct.message.stream;

import com.evolution.direct.message.event.MessageDenormalizationStateEvent;
import com.evolution.direct.message.layer.query.model.Message;
import com.evolution.direct.message.layer.query.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Optional;

@EnableBinding({Sink.class})
public class MessageSinkStateProcessor {

    private final static Logger logger = LoggerFactory.getLogger(MessageSinkStateProcessor.class);

    @Autowired
    private MessageRepository messageRepository;

    @StreamListener(Sink.INPUT)
    public void processState(MessageDenormalizationStateEvent event) {
        logger.info("Catch MessageDenormalizationStateEvent:" + event);

        Message message = new Message();

        Optional<Message> original = messageRepository.findById(event.getId());
        if (original.isPresent()) {
            message = original.get();
        }

        message.setText(event.getText());
        message.setSender(event.getSender());
        message.setRecipient(event.getRecipient());
        message.setPostDate(event.getPostDate());
        message.setPutDate(event.getPutDate());
        message.setRead(event.isRead());
        message.setEventId(event.getEventId());

        messageRepository.save(message);
    }
}
