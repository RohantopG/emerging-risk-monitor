package com.internship.tool.controller;

import com.internship.tool.service.AuthService;
import com.internship.tool.dto.AuthResponseDTO;
import com.internship.tool.dto.LoginRequestDTO;
import com.internship.tool.dto.RegisterRequestDTO;
import com.internship.tool.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.status(201)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(
                authService.login(request));
    }
}