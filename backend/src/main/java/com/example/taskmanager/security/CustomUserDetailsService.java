package com.example.taskmanager.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.taskmanager.repository.UserRepository;

// @Service = Spring verwaltet diese Klasse als "Service-Bean"
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Hier kommt unser UserRepository rein (Datenbankzugriff)
    private final UserRepository userRepository;

    // Konstruktor-Injection: Spring gibt uns automatisch das Repository
    // (besser als @Autowired auf Felder direkt - leichter testbar)
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Spring Security ruft diese Methode auf wenn es einen User laden will
    // z.B. beim Login oder beim Pruefen des JWT-Tokens
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Suche User in der Datenbank
        // findByUsername gibt ein Optional<User> zurueck
        // orElseThrow: wenn kein User gefunden -> Exception werfen
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Benutzer nicht gefunden: " + username));
        // Unser User implementiert UserDetails, daher koennen wir ihn direkt zurueckgeben
    }
}
