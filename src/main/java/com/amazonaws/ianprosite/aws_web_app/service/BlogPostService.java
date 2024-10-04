package com.amazonaws.ianprosite.aws_web_app.service;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;

import java.util.List;
import java.util.Optional;

public interface BlogPostService {
    BlogPost createBlogPost(BlogPost blogPost);
    List<BlogPost> getAllBlogPosts();
    Optional<BlogPost> getBlogPostById(Long id);
    BlogPost updateBlogPost(Long id, BlogPost blogPost);
    boolean deleteBlogPost(Long id);
}