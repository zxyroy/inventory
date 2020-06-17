package com.zxyroy.inventory.controller;

import com.zxyroy.inventory.exeption.CategoryNotMatchException;
import com.zxyroy.inventory.exeption.ResourseNotFoundExeption;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler {
    @ExceptionHandler(CategoryNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> springHandleNotFound(CategoryNotMatchException apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", "Sub-category not belong to category");
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }

    @ExceptionHandler(ResourseNotFoundExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public HashMap<String, Object> springHandleNotFound(ResourseNotFoundExeption apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", "Resource not found");
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HashMap<String, Object> springHandleNotFound(IllegalArgumentException apiException) {
        HashMap<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", System.currentTimeMillis());
        errorResponse.put("message", "Input Illegal");
        log.info("Invalid Request {}", errorResponse);
        return errorResponse;
    }
}
