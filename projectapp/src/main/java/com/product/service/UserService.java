package com.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dto.LoginDTO;
import com.product.dto.UserDTO;
import com.product.entity.User;
import com.product.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; 

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        // Populate additional fields from UserDTO
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress()); // Make sure this can handle null values since address is optional 
        String pincodeStr = userDTO.getPincode();
        int pincode = Integer.parseInt(pincodeStr); 
        if(pincode>0) {
        	  user.setPincode(pincodeStr); 
        }  
        userRepository.save(user);
        userRepository.flush();
        return user;
    } 

    public User loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null ) {
            return user;
        }
        return null;  
    }
}
