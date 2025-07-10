package com.tekup.taskmanager.controllers;

import com.tekup.taskmanager.dto.AuthenticationRequest;
import com.tekup.taskmanager.dto.AuthenticationResponse;
import com.tekup.taskmanager.dto.RegisterRequest;
import com.tekup.taskmanager.dto.RegisterResponse;
import com.tekup.taskmanager.dto.UsernameCheckResponse;
import com.tekup.taskmanager.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<UsernameCheckResponse> checkUsernameAvailability(@PathVariable String username) {
        return ResponseEntity.ok(authenticationService.checkUsernameAvailability(username));
    }
} 