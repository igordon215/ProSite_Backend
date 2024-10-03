package com.amazonaws.ianprosite.aws_web_app.service;

import com.amazonaws.ianprosite.aws_web_app.model.Project;
import com.amazonaws.ianprosite.aws_web_app.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project updateProject(Long id, Project project) {
        Optional<Project> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent()) {
            Project updatedProject = existingProject.get();
            updatedProject.setTitle(project.getTitle());
            updatedProject.setName(project.getName());
            updatedProject.setDescription(project.getDescription());
            updatedProject.setRepoUrl(project.getRepoUrl());
            updatedProject.setLiveUrl(project.getLiveUrl());
            updatedProject.setUpdatedAt(LocalDateTime.now());
            return projectRepository.save(updatedProject);
        }
        return null;
    }

    @Override
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}