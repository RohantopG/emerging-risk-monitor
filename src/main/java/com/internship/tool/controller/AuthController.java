package com.internship.tool.controller;

import com.internship.tool.dto.*;
import com.internship.tool.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO req) {

        System.out.println("LOGIN API HIT");   // 👈 ADD THIS

        return ResponseEntity.ok(service.login(req));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody RegisterRequestDTO req) {

        System.out.println("REGISTER HIT"); // 👈 DEBUG

        return ResponseEntity.status(201).body(service.register(req));
    }
}