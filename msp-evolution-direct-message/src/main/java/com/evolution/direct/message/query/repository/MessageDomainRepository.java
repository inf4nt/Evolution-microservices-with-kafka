package com.evolution.direct.message.query.repository;

import com.evolution.direct.message.query.model.MessageModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageDomainRepository extends MongoRepository<MessageModel, String> {
}
