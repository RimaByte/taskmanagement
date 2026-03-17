package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

// Dieses DTO empfangen wir vom Client beim Login
// Der Client schickt: { "username": "max", "password": "1234" }
public class LoginRequestDto {

    @NotBlank(message = "Username darf nicht leer sein")
    private String username;

    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
