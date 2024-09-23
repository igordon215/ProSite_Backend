package com.amazonaws.ianprosite.aws_web_app.config;

import com.amazonaws.ianprosite.aws_web_app.model.BlogPost;
import com.amazonaws.ianprosite.aws_web_app.model.Project;
import com.amazonaws.ianprosite.aws_web_app.model.User;
import com.amazonaws.ianprosite.aws_web_app.repository.BlogPostRepository;
import com.amazonaws.ianprosite.aws_web_app.repository.ProjectRepository;
import com.amazonaws.ianprosite.aws_web_app.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        loadBlogPosts();
        loadProjects();
        loadUsers();

        // Print out the count of entities in each table
        System.out.println("BlogPosts count: " + blogPostRepository.count());
        System.out.println("Projects count: " + projectRepository.count());
        System.out.println("Users count: " + userRepository.count());
    }

    private void loadBlogPosts() throws IOException {
        if (blogPostRepository.count() == 0) {
            List<BlogPost> blogPosts = objectMapper.readValue(new File("blog_posts.json"), new TypeReference<List<BlogPost>>() {});
            blogPostRepository.saveAll(blogPosts);
            System.out.println("Blog posts loaded: " + blogPosts.size());
        }
    }

    private void loadProjects() throws IOException {
        if (projectRepository.count() == 0) {
            List<Project> projects = objectMapper.readValue(new File("projects.json"), new TypeReference<List<Project>>() {});
            List<Project> validProjects = projects.stream()
                .filter(project -> project.getName() != null || project.getTitle() != null)
                .map(this::ensureNameField)
                .collect(Collectors.toList());
            projectRepository.saveAll(validProjects);
            System.out.println("Projects loaded: " + validProjects.size());
        }
    }

    private Project ensureNameField(Project project) {
        if (project.getName() == null) {
            project.setName(project.getTitle());
        }
        if (project.getTitle() == null) {
            project.setTitle(project.getName());
        }
        return project;
    }

    private void loadUsers() throws IOException {
        if (userRepository.count() == 0) {
            List<User> users = objectMapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});
            userRepository.saveAll(users);
            System.out.println("Users loaded: " + users.size());
        }
    }
}