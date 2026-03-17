package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Diese Methode konfiguriert die Sicherheitsfilter für deine Webanwendung

        http
        .authorizeHttpRequests((authz) -> authz
        .requestMatchers("/login", "/register").permitAll().anyRequest().authenticated())
        .formLogin((form) -> form
        .loginPage("/login")  // Definiert die benutzerdefinierte Login-Seite
        .defaultSuccessUrl("/projects", true)  // Nach erfolgreichem Login weiterleiten
        .permitAll())  // Erlaubt allen Benutzern den Zugriff auf die Login-Seite
        .logout((logout) -> logout.permitAll());
                
                
                
                // Alle anderen Anfragen erfordern Authentifizierung
        return http.build();  // Gibt den konfigurierten Filter zurück
    }
}