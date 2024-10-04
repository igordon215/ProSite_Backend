package com.amazonaws.ianprosite.aws_web_app.controller;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import com.amazonaws.ianprosite.aws_web_app.service.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blog-posts")
public class BlogPostController {

    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);

    @Autowired
    private BlogPostService blogPostService;

    // GET methods are public by default (configured in WebSecurityConfig)
    // POST, PUT, and DELETE methods require authentication

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createBlogPost(@RequestBody BlogPost blogPost) {
        try {
            BlogPost savedBlogPost = blogPostService.createBlogPost(blogPost);
            logger.info("Blog post created successfully: {}", savedBlogPost.getId());
            return new ResponseEntity<>(savedBlogPost, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating blog post", e);
            return new ResponseEntity<>("Error creating blog post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBlogPosts() {
        try {
            List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
            return new ResponseEntity<>(blogPosts, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving blog posts", e);
            return new ResponseEntity<>("Error retrieving blog posts: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogPostById(@PathVariable Long id) {
        try {
            Optional<BlogPost> blogPostOptional = blogPostService.getBlogPostById(id);
            if (blogPostOptional.isPresent()) {
                return ResponseEntity.ok(blogPostOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog post not found");
            }
        } catch (Exception e) {
            logger.error("Error retrieving blog post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving blog post: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost) {
        try {
            BlogPost updatedBlogPost = blogPostService.updateBlogPost(id, blogPost);
            if (updatedBlogPost != null) {
                logger.info("Blog post updated successfully: {}", id);
                return ResponseEntity.ok(updatedBlogPost);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog post not found");
            }
        } catch (Exception e) {
            logger.error("Error updating blog post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating blog post: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteBlogPost(@PathVariable Long id) {
        try {
            if (blogPostService.deleteBlogPost(id)) {
                logger.info("Blog post deleted successfully: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog post not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting blog post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting blog post: " + e.getMessage());
        }
    }
}