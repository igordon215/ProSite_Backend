package com.amazonaws.ianprosite.aws_web_app.repository;

import com.amazonaws.ianprosite.aws_web_app.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}