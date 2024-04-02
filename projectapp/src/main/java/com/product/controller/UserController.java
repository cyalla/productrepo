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
    } catch (IllegalArgumentException e) {
        // Handle specific IllegalArgumentException with custom logic or error response
        log.error("IllegalArgumentException occurred during user registration:", e);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
        // Handle generic Exception with custom logic or error response
        log.error("An error occurred during user registration:", e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
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
