package com.amazonaws.ianprosite.aws_web_app.controller;

import com.amazonaws.ianprosite.aws_web_app.model.Project;
import com.amazonaws.ianprosite.aws_web_app.service.ProjectService;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project savedProject = projectService.createProject(project);
            logger.info("Project created successfully: {}", savedProject.getId());
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating project", e);
            return new ResponseEntity<>("Error creating project: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving projects", e);
            return new ResponseEntity<>("Error retrieving projects: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        try {
            Optional<Project> projectOptional = projectService.getProjectById(id);
            if (projectOptional.isPresent()) {
                return ResponseEntity.ok(projectOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }
        } catch (Exception e) {
            logger.error("Error retrieving project with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving project: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            if (updatedProject != null) {
                logger.info("Project updated successfully: {}", id);
                return ResponseEntity.ok(updatedProject);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }
        } catch (Exception e) {
            logger.error("Error updating project with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating project: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            if (projectService.deleteProject(id)) {
                logger.info("Project deleted successfully: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting project with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting project: " + e.getMessage());
        }
    }
}