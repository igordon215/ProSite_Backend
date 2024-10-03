package com.amazonaws.ianprosite.aws_web_app.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}