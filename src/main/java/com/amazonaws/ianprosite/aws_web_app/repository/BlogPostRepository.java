package com.amazonaws.ianprosite.aws_web_app.repository;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogPostRepository extends MongoRepository<BlogPost, String> {
}