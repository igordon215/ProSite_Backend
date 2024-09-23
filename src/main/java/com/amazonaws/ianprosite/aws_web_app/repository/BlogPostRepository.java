package com.amazonaws.ianprosite.aws_web_app.repository;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}