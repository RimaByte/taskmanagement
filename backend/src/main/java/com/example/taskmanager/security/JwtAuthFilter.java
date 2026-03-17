package com.example.taskmanager.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// OncePerRequestFilter = dieser Filter laeuft genau EINMAL pro HTTP-Anfrage
// (nicht mehrfach, auch wenn die Filter-Kette mehrere Durchlaeufe hat)
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,      // Die eingehende HTTP-Anfrage
            HttpServletResponse response,    // Die ausgehende HTTP-Antwort
            FilterChain filterChain          // Der Rest der Filter-Kette
    ) throws ServletException, IOException {

        // 1. Authorization-Header lesen
        // Erwartetes Format: "Bearer eyJhbGciOiJIUzI1NiJ9..."
        String authHeader = request.getHeader("Authorization");

        // Wenn kein Header vorhanden oder nicht mit "Bearer " anfaengt:
        // -> Anfrage weiterleiten OHNE Authentifizierung (wird spaeter von Security blockiert)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. "Bearer " entfernen, nur den Token selbst behalten
        // "Bearer " hat 7 Zeichen, daher substring(7)
        String token = authHeader.substring(7);

        // 3. Token validieren und Username extrahieren
        if (!jwtUtil.isTokenValid(token)) {
            // Ungueltig -> weiterleiten (Security blockiert dann)
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.extractUsername(token);

        // 4. Nur wenn noch keine Authentifizierung gesetzt ist (verhindert doppelte Arbeit)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // User aus der Datenbank laden
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Authentifizierungs-Objekt erstellen
            // Parameter: (wer?, credentials=null bei JWT, welche Rechte?)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,                          // Bei JWT kein Passwort noetig
                            userDetails.getAuthorities()   // Rechte des Users (ROLE_USER, etc.)
                    );

            // Zusatzinfos hinzufuegen (z.B. IP-Adresse der Anfrage)
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Spring Security mitteilen: dieser User ist jetzt authentifiziert
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 5. Naechsten Filter in der Kette ausfuehren (oder den Controller aufrufen)
        filterChain.doFilter(request, response);
    }
}
