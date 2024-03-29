package com.product.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active=test")
public class UserIntegrationTests {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void registerUserTest() {
    	String baseUrl = "http://localhost:8080/productapp/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Generate a unique username with UUID
        String uniqueUsername = "testuser" + UUID.randomUUID().toString();
        // Fill in all the fields
        String requestJson = String.format(
                "{\"username\":\"%s\",\"password\":\"password\",\"firstName\":\"Test\",\"lastName\":\"User\",\"address\":\"123 Test Lane\",\"pincode\":\"12345\"}",
                uniqueUsername);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
