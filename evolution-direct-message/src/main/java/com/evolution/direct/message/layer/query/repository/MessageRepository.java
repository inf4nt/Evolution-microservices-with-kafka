package com.evolution.direct.message.layer.query.repository;

import com.evolution.direct.message.layer.query.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "message")
public interface MessageRepository extends MongoRepository<Message, String> {
}
