package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Klasse heisst jetzt PasswordEncoderConfig, nicht PasswordEncoder
// Problem vorher: Klasse "PasswordEncoder" + @Bean-Methode "passwordEncoder()"
// hatten denselben Bean-Namen -> Spring-Konflikt
// Jetzt: Klasse heisst anders -> kein Konflikt mehr
@Configuration
public class PasswordEncoderConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
