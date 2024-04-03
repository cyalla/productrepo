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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public ResponseEntity<Optional<User>> registerUser(@RequestBody UserDTO userDTO) {
        // Empty check for userDTO
        if (userDTO == null || userDTO.isEmpty()) {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
        }

        Optional<User> registeredUser = userService.registerUser(userDTO);
        if (registeredUser.isPresent()) {
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

class UserDTO {
    private String username;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return username.equals(userDTO.username) &&
                password.equals(userDTO.password);
    }

    @Override
    public int hashCode() {
        return 31 * username.hashCode() + 31 * password.hashCode();
    }
}

class User {
    private Long id;
    private String username;
    private String password;
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
