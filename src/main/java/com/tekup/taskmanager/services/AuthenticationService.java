package com.tekup.taskmanager.services;

import com.tekup.taskmanager.dto.AuthenticationRequest;
import com.tekup.taskmanager.dto.AuthenticationResponse;
import com.tekup.taskmanager.dto.RegisterRequest;
import com.tekup.taskmanager.dto.RegisterResponse;
import com.tekup.taskmanager.dto.UsernameCheckResponse;

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    UsernameCheckResponse checkUsernameAvailability(String username);
} 