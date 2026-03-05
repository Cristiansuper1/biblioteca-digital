package com.example.bibliotecadigital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Permite todas as requisições sem autenticação
                )
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF (proteção contra ataques)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())  // Permite o H2 Console funcionar
                );

        return http.build();
    }
}
