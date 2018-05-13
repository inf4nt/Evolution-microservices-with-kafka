package com.evolution.user.query.repository;

import com.evolution.user.query.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserModelRepository extends MongoRepository<UserModel, String> {
}
