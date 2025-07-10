package com.tekup.taskmanager.services;

import com.tekup.taskmanager.dto.AuthenticationRequest;
import com.tekup.taskmanager.dto.AuthenticationResponse;
import com.tekup.taskmanager.dto.RegisterRequest;
import com.tekup.taskmanager.dto.RegisterResponse;
import com.tekup.taskmanager.dto.UsernameCheckResponse;
import com.tekup.taskmanager.entities.Role;
import com.tekup.taskmanager.entities.User;
import com.tekup.taskmanager.repositories.RoleRepository;
import com.tekup.taskmanager.repositories.UserRepository;
import com.tekup.taskmanager.exception.AuthenticationException;
import com.tekup.taskmanager.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new AuthenticationException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthenticationException("Email already exists");
        }

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Set roles
        Set<Role> roles = new HashSet<>();
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName(roleName);
                            return roleRepository.save(newRole);
                        });
                roles.add(role);
            }
        } else {
            // Default role if none specified
            Role defaultRole = roleRepository.findByName("USER")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("USER");
                        return roleRepository.save(newRole);
                    });
            roles.add(defaultRole);
        }
        user.setRoles(roles);

        userRepository.save(user);

        return RegisterResponse.builder()
                .message("User registered successfully")
                .build();
    }

    @Override
    public UsernameCheckResponse checkUsernameAvailability(String username) {
        boolean isAvailable = !userRepository.findByUsername(username).isPresent();
        
        return UsernameCheckResponse.builder()
                .username(username)
                .available(isAvailable)
                .message(isAvailable ? "Username is available" : "Username already exists")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsernameWithRoles(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
} 