package com.amazonaws.ianprosite.aws_web_app.controller;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import com.amazonaws.ianprosite.aws_web_app.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog-posts")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        blogPost.setCreatedAt(LocalDateTime.now());
        blogPost.setUpdatedAt(LocalDateTime.now());
        BlogPost savedBlogPost = blogPostRepository.save(blogPost);
        return new ResponseEntity<>(savedBlogPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        Optional<BlogPost> existingBlogPost = blogPostRepository.findById(id);
        if (existingBlogPost.isPresent()) {
            blogPost.setId(id);
            blogPost.setCreatedAt(existingBlogPost.get().getCreatedAt());
            blogPost.setUpdatedAt(LocalDateTime.now());
            BlogPost updatedBlogPost = blogPostRepository.save(blogPost);
            return new ResponseEntity<>(updatedBlogPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            blogPostRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}