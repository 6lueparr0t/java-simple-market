package com.rgbplace.controller;

import com.rgbplace.domain.Greeting;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final String APPLICATION_JSON_VALUE = "application/json";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping(value = "/healthcheck")
    public ResponseEntity<Map<String, String>> healthcheck(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        String format = request.getParameter("format");
        if(format == null) {
            return ResponseEntity.badRequest().headers(httpHeaders).body(null);
        }

        Map<String, String> result = new HashMap<>();

        if("full".equals(format)) {
            result.put("currentTime", formatIsoUtcDateTime());
            result.put("status", "OK");
        } else if("short".equals(format)) {
            result.put("status", "OK");
        } else {
            return ResponseEntity.badRequest().headers(httpHeaders).body(null);
        }

        return ResponseEntity.ok().headers(httpHeaders).body(result);
    }

    @PutMapping(value = "/healthcheck")
    public ResponseEntity<String> healthcheckPut() {
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping(value = "/healthcheck")
    public ResponseEntity<String> healthcheckPost() {
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/healthcheck")
    public ResponseEntity<String> healthcheckDelete() {
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }

    public String formatIsoUtcDateTime() {
        return ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.of("UTC")).toOffsetDateTime().toString();
    }
}
