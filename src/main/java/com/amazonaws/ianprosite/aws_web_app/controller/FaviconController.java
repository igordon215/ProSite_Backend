package com.amazonaws.ianprosite.aws_web_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaviconController {

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> returnNoFavicon() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}