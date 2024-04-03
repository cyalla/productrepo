package com.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.LoginDTO;
import com.product.dto.UserDTO;
import com.product.entity.User;
import com.product.service.UserService;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
        User newUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    } catch (Exception e) {
        return handleRegistrationException(e);
    }
}

private ResponseEntity<?> handleRegistrationException(Exception e) {
    // Log the exception and return an appropriate response
    logger.error("An error occurred during user registration:", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed.");
}
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        User user = userService.loginUser(loginDTO);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
