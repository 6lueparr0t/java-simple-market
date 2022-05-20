package com.rgbplace.controller;

import com.rgbplace.common.constant.ExceptionMessage;
import com.rgbplace.common.constant.StatusMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleLineException(Exception e) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        Map<String, String> result = new HashMap<>();
        result.put("status", StatusMessage.FAIL.getValue());
        result.put("msg", ExceptionMessage.ERROR.getValue());

        return ResponseEntity.ok().headers(httpHeaders).body(result);
    }
}

