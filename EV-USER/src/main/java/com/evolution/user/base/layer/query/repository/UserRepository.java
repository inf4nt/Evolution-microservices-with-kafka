package com.evolution.user.base.layer.query.repository;

import com.evolution.user.base.layer.query.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "user")
public interface UserRepository extends MongoRepository<UserModel, String> {

    Optional<UserModel> findByUsername(String username);
}
