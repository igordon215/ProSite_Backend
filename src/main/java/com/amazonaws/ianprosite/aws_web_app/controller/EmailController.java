package com.amazonaws.ianprosite.aws_web_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ian.gordon.codes@gmail.com");
        message.setSubject("New Contact Form Submission");
        message.setText(
            "Name: " + emailRequest.getName() + "\n" +
            "Email: " + emailRequest.getEmail() + "\n" +
            "Message: " + emailRequest.getMessage()
        );

        emailSender.send(message);

        return "Email sent successfully";
    }

    public static class EmailRequest {
        private String name;
        private String email;
        private String message;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
