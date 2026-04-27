package com.internship.tool.service;

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

    public AuthResponseDTO register(RegisterRequestDTO req) {

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("VIEWER");

        userRepository.save(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setMessage("Registered successfully");

        return response;
    }

    public AuthResponseDTO login(LoginRequestDTO req) {

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        AuthResponseDTO response = new AuthResponseDTO();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setMessage("Login successful");
        response.setToken("temp-token");

        return response;
    }
}