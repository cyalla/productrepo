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

import java.time.Instant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active=test")
public class ProductIntegrationTests {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createProductTest() {
        String baseUrl = "http://localhost:8080/productapp/products";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Append a timestamp to the product name
        String uniqueProductName = "Test Product " + Instant.now().toEpochMilli();
        String requestJson = String.format("{\"name\":\"%s\",\"price\":\"100\",\"tax\":\"10\"}", uniqueProductName);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Further assert statements based on the response body
    }
}

