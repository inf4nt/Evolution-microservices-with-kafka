package com.evolution.command.execute.query.repository;

import com.evolution.command.execute.query.model.CommandExecuteStatusModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "execute_status")
public interface CommandExecuteStatusRepository extends MongoRepository<CommandExecuteStatusModel, String> {
}
