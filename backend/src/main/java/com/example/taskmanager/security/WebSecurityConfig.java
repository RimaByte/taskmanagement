package com.example.taskmanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    // Spring injiziert alle drei automatisch (weil sie @Component / @Bean haben)
    public WebSecurityConfig(
            JwtAuthFilter jwtAuthFilter,
            CustomUserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF deaktivieren: CSRF ist fuer Browser/Session-Anwendungen
            // REST APIs mit JWT brauchen keinen CSRF-Schutz
            .csrf(csrf -> csrf.disable())

            // CORS aktivieren: erlaubt Anfragen von Angular (localhost:4200)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Endpunkte konfigurieren: wer darf was aufrufen?
            .authorizeHttpRequests(auth -> auth
                // Diese Endpunkte sind oeffentlich (kein Token noetig)
                .requestMatchers("/auth/login", "/register").permitAll()
                // Alle anderen Endpunkte erfordern einen gueltigen JWT-Token
                .anyRequest().authenticated()
            )

            // Session-Management: STATELESS = keine Server-Sessions
            // Jede Anfrage traegt den Token selbst mit, der Server merkt sich nichts
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Unseren JWT-Filter VOR den Standard-Login-Filter einfuegen
            // Reihenfolge: JwtAuthFilter -> UsernamePasswordAuthenticationFilter -> Controller
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    // Welche Origins (Domains/Ports) dürfen Anfragen schicken?
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));   // Angular
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));    // alle Header erlaubt (inkl. Authorization)
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // gilt für alle Endpunkte
        return source;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
