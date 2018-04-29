package com.evolution.user.base.layer.query.repository;

import com.evolution.user.base.layer.query.model.CommandStatusModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "command-status")
public interface CommandRepository extends MongoRepository<CommandStatusModel, String> {
}
