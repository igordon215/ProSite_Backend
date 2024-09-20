package com.amazonaws.ianprosite.aws_web_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String title;
    private String description;
    private String repoUrl;
    private String liveUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}