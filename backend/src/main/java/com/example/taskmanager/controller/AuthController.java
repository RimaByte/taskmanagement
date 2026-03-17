package com.example.taskmanager.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.LoginRequestDto;
import com.example.taskmanager.dto.LoginResponseDto;
import com.example.taskmanager.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // POST /auth/login
    // Client schickt: { "username": "max", "password": "1234" }
    // Server antwortet: { "token": "eyJhbGci..." }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {

        // AuthenticationManager prueft Username + Passwort gegen die DB
        // Falls falsch -> wirft BadCredentialsException -> Spring gibt 401 zurueck
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        // Wer hat sich authentifiziert? getName() gibt den Username zurueck
        String username = authentication.getName();

        // JWT-Token erstellen
        String token = jwtUtil.generateToken(username);

        // Token im Response-Body zurueckschicken
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
