package com.amazonaws.ianprosite.aws_web_app.service;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import com.amazonaws.ianprosite.aws_web_app.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public BlogPost createBlogPost(BlogPost blogPost) {
        blogPost.setCreatedAt(LocalDateTime.now());
        blogPost.setUpdatedAt(LocalDateTime.now());
        return blogPostRepository.save(blogPost);
    }

    @Override
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public Optional<BlogPost> getBlogPostById(Long id) {
        return blogPostRepository.findById(id);
    }

    @Override
    public BlogPost updateBlogPost(Long id, BlogPost blogPost) {
        Optional<BlogPost> existingBlogPost = blogPostRepository.findById(id);
        if (existingBlogPost.isPresent()) {
            BlogPost updatedBlogPost = existingBlogPost.get();
            updatedBlogPost.setTitle(blogPost.getTitle());
            updatedBlogPost.setContent(blogPost.getContent());
            updatedBlogPost.setUpdatedAt(LocalDateTime.now());
            return blogPostRepository.save(updatedBlogPost);
        }
        return null;
    }

    @Override
    public boolean deleteBlogPost(Long id) {
        if (blogPostRepository.existsById(id)) {
            blogPostRepository.deleteById(id);
            return true;
        }
        return false;
    }
}