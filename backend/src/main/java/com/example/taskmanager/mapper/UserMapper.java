package com.example.taskmanager.mapper;

import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.UserRequestDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.entity.Role;
import com.example.taskmanager.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// BCryptPasswordEncoder direkt injizieren — nicht die Konfigurationsklasse
@Component
public class UserMapper{

    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRequestDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Verschlüsselt das Passwort mit BCrypt
        user.setRole(Role.USER);

        return user;
    }

    public UserResponseDto toResponseDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        return dto;
    }
}

