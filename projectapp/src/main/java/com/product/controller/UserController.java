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
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
    try {
        User newUser = userService.registerUser(userDTO);
        if (newUser == null) {
            throw new NullPointerException("User object is null after registration.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    } catch (IllegalArgumentException e) {
        // Handle illegal argument exception, e.g., invalid user data
        logIllegalArgumentException(e);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        // Handle other exceptions, e.g., database errors, invalid user data
        logException(e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

private void logIllegalArgumentException(IllegalArgumentException e) {
    // Log the illegal argument exception with appropriate details
    logger.error("Illegal argument exception occurred: ", e);
}

private void logException(Exception e) {
    // Log the exception with appropriate details
    logger.error("An error occurred during user registration:", e);
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
