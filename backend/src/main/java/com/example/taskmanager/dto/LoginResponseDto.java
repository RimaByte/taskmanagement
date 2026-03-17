package com.example.taskmanager.dto;

// Dieses DTO schicken wir nach erfolgreichem Login zurueck
// Die Antwort: { "token": "eyJhbGci..." }
public class LoginResponseDto {

    private String token;

    // Konstruktor: direkt beim Erstellen den Token mitgeben
    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
