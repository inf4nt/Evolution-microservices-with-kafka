package com.evolution.direct.message.stream;

import com.evolution.direct.message.event.MessageStateEvent;
import com.evolution.direct.message.layer.query.model.Message;
import com.evolution.direct.message.layer.query.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.Optional;

@EnableBinding(Sink.class)
public class MessageStateSinkStream {

    @Autowired
    private MessageRepository messageRepository;

    @StreamListener(Sink.INPUT)
    public void sinkState(MessageStateEvent event) {
        Message message = new Message();
        Optional<Message> original = messageRepository.findById(event.getId());
        if (original.isPresent()) {
            // message exist 
            message = original.get();
        }

        message.setId(event.getId());
        message.setEventId(event.getEventId());
        message.setText(event.getText());
        message.setSender(event.getSender());
        message.setRecipient(event.getRecipient());
        message.setRead(event.isRead());
        message.setPostDate(event.getPostDate());
        message.setPutDate(event.getPutDate());

        messageRepository.save(message);
    }
}
