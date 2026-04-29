package com.internship.tool.controller;

import com.internship.tool.dto.RegisterRequestDTO;
import com.internship.tool.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody RegisterRequestDTO request) {
        return authService.login(request.getEmail(), request.getPassword());
    }
}