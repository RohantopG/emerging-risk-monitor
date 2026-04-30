package com.internship.tool.service;

import com.internship.tool.config.JwtUtil;
import com.internship.tool.dto.*;
import com.internship.tool.entity.User;
import com.internship.tool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("VIEWER")
                .isActive(true)
                .build();

        userRepository.save(user);

        return AuthResponseDTO.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .message("User registered successfully")
                .build();
    }

    // LOGIN
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is disabled");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return AuthResponseDTO.builder()
                .token(token)
                .username(user.getUsername())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
}