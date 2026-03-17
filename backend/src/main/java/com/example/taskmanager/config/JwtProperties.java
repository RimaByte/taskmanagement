package com.example.taskmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// @ConfigurationProperties(prefix = "jwt") sagt Spring:
// "Lies alle Properties die mit 'jwt.' anfangen und binde sie an diese Klasse"
//
// jwt.secret     -> wird in das Feld 'secret' geschrieben
// jwt.expiration -> wird in das Feld 'expiration' geschrieben
//
// Vorteil 1: Die IDE erkennt jetzt jwt.* als gueltige Properties
// Vorteil 2: Alle JWT-Einstellungen an einem Ort
// Vorteil 3: Typ-sicher (expiration ist ein long, kein String)
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private long expiration;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public long getExpiration() { return expiration; }
    public void setExpiration(long expiration) { this.expiration = expiration; }
}
