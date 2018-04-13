package com.evolution.user.layer.query.repository;

import com.evolution.user.layer.query.model.UserState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "user")
public interface UserRepository extends MongoRepository<String, UserState> {
}
