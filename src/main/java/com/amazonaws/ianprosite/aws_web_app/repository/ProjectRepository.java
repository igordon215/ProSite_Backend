package com.amazonaws.ianprosite.aws_web_app.repository;

import com.amazonaws.ianprosite.aws_web_app.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}