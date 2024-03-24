package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Register the Application class as a configuration class of Spring
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        // Launch the application
        SpringApplication.run(Application.class, args);
    }
}
