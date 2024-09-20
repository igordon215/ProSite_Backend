package com.amazonaws.ianprosite.aws_web_app.repository;

import com.amazonaws.ianprosite.aws_web_app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}