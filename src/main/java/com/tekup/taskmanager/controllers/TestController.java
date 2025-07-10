package com.tekup.taskmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<Map<String, String>> publicEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint - no authentication required");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> userEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a user endpoint - authentication required");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> adminEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is an admin endpoint - admin role required");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        return ResponseEntity.ok(response);
    }
} 