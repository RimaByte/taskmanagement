package com.example.taskmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.taskmanager.dto.UserRequestDto;
import com.example.taskmanager.dto.UserResponseDto;
import com.example.taskmanager.entity.Role;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.security.PasswordEncoder;

@Component
public class UserMapper{
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toEntity(UserRequestDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.passwordEncoder().encode(dto.getPassword())); // Verschlüsselt das Passwort mit BCrypt
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

