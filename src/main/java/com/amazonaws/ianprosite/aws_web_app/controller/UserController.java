package com.amazonaws.ianprosite.aws_web_app.controller;

import com.amazonaws.ianprosite.aws_web_app.model.User;
import com.amazonaws.ianprosite.aws_web_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Temporary endpoint to check user data
    @GetMapping("/check/{username}")
    public ResponseEntity<?> checkUser(@PathVariable String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User foundUser = user.get();
            // Don't return the actual password, just its length and whether it starts with '$2a$'
            String passwordInfo = "Length: " + foundUser.getPassword().length() + 
                                  ", BCrypt: " + foundUser.getPassword().startsWith("$2a$");
            return ResponseEntity.ok("User found. Username: " + foundUser.getUsername() + 
                                     ", Password info: " + passwordInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    // Existing endpoints...

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Other existing methods...
}