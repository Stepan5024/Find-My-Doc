package ru.aidoc.apigateway.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

/**
 * Конфигурация SecurityContextRepository для WebFlux.
 */
@Configuration
public class SecurityContextRepositoryConfig {

    private final ReactiveAuthenticationManager authenticationManager;

    public SecurityContextRepositoryConfig(ReactiveAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

  
}