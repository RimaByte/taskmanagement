package com.example.taskmanager.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.taskmanager.config.JwtProperties;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Statt @Value fuer jede einzelne Property injizieren wir jetzt
    // die gesamte JwtProperties-Klasse — sauberer und uebersichtlicher
    private final JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    // Erstellt einen sicheren Schluessel aus dem Secret-String
    // Der Schluessel muss mindestens 256 Bit haben fuer HS256
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ---- Token ERSTELLEN ----
    // Wird nach erfolgreichem Login aufgerufen
    // subject = der "Inhalt" des Tokens, hier der Username
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)                          // Wer ist der Token fuer?
                .issuedAt(new Date())                       // Wann wurde er erstellt?
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration())) // Wann laeuft er ab?
                .signWith(getSigningKey())                  // Mit welchem Key wird signiert?
                .compact();                                 // Alles zusammenbauen -> String
    }

    // ---- Username LESEN ----
    // Liest den Username aus einem vorhandenen Token heraus
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // Pruefe die Signatur mit unserem Key
                .build()
                .parseSignedClaims(token)     // Token entschluesseln
                .getPayload()                 // Den Inhalt holen
                .getSubject();                // Den "subject" (= Username) zurueckgeben
    }

    // ---- Token VALIDIEREN ----
    // Gibt true zurueck wenn der Token gueltig ist (richtige Signatur + nicht abgelaufen)
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token); // wirft Exception wenn ungueltig oder abgelaufen
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token ist gefaelscht, abgelaufen, oder leer -> ungueltig
            return false;
        }
    }
}
