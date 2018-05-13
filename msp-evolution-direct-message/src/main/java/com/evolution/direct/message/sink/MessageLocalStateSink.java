package com.evolution.direct.message.sink;

import com.evolution.direct.message.core.domain.MessageDomain;
import com.evolution.direct.message.query.model.MessageModel;
import com.evolution.direct.message.query.repository.MessageDomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import javax.validation.Valid;
import java.util.Optional;

@EnableBinding(Sink.class)
public class MessageLocalStateSink {

    private final static Logger logger = LoggerFactory.getLogger(MessageLocalStateSink.class);

    private final MessageDomainRepository messageDomainRepository;

    @Autowired
    public MessageLocalStateSink(MessageDomainRepository messageDomainRepository) {
        this.messageDomainRepository = messageDomainRepository;
    }

    @StreamListener(Sink.INPUT)
    public void sink(@Valid MessageDomain domain) {
        logger.info("Catch domain:" + domain);
        MessageModel model = MessageModel.builder()
                .key(domain.getKey())
                .eventNumber(domain.getEventNumber())
                .sender(domain.getSender())
                .recipient(domain.getRecipient())
                .text(domain.getText())
                .isRead(domain.isRead())
                .build();

        messageDomainRepository.save(model);
    }
}
