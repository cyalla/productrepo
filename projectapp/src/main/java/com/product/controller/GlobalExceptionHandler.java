package com.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    // Handler for specific exceptions
    // Example: IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String stackTrace = getStackTrace(ex);
        System.out.println("Exception in GlobalExceptionHandler::handleIllegalArgumentException::"+stackTrace);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false), stackTrace);
    }

    // Generic exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        String stackTrace = getStackTrace(ex);
        System.out.println("Exception in GlobalExceptionHandler::handleAllExceptions::"+stackTrace);
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false), stackTrace);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, String path, String stackTrace) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        // Include stack trace only if not empty or null
        if (stackTrace != null && !stackTrace.isEmpty()) {
            body.put("trace", stackTrace);
        }

        return new ResponseEntity<>(body, status);
    }

    // Utility method to get stack trace from an exception
    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }
    
    public void handleException(Exception e) {
        // Log the exception with a custom message
        logger.error("An exception occurred: ", e);
        
    }
}
