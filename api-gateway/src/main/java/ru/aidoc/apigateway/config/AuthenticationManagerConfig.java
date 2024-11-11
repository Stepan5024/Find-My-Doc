package ru.aidoc.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.utils.JwtUtil;

/**
 * Конфигурация ReactiveAuthenticationManager.
 */
@Configuration
public class AuthenticationManagerConfig {

    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    public AuthenticationManagerConfig(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> {
            String authToken = authentication.getCredentials().toString();
            if (jwtUtil.validateToken(authToken)) {
                String username = jwtUtil.getUsernameFromToken(authToken);
                return userDetailsService.findByUsername(username)
                        .map(userDetails -> new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities()))
                        .cast(Authentication.class);
            } else {
                return Mono.empty();
            }
        };
    }
}
