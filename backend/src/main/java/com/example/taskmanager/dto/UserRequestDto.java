package com.example.taskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    // @NotBlank = nicht null, nicht leer, nicht nur Leerzeichen
    // message = was der Client als Fehlermeldung bekommt
    @NotBlank(message = "Username darf nicht leer sein")
    @Size(min = 3, max = 50, message = "Username muss zwischen 3 und 50 Zeichen lang sein")
    private String username;

    // @Email prueft ob das Format x@y.z ist
    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "Ungueltige E-Mail-Adresse")
    private String email;

    // Passwort: mindestens 6 Zeichen fuer etwas Sicherheit
    @NotBlank(message = "Passwort darf nicht leer sein")
    @Size(min = 6, message = "Passwort muss mindestens 6 Zeichen lang sein")
    private String password;

    // Rolle ist optional bei der Registrierung (kann auch ohne Rolle erstellt werden)
    private String role;

    
    public UserRequestDto() {
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
