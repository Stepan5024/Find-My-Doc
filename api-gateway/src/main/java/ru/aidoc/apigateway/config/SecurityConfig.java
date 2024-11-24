package ru.aidoc.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.service.JwtAuthenticationFilter;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(ReactiveAuthenticationManager authenticationManager,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        log.info("Configuring SecurityWebFilterChain");

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> {
                    log.info("Configuring authorization rules");
                    exchanges
                            .pathMatchers(HttpMethod.POST, "/auth/**").permitAll()
                            .pathMatchers("/", "/login", "/dashboard","/register", "/css/**", "/js/**", "/images/**", "/webjars/**", "/dashboard").permitAll()
                            .anyExchange().authenticated();
                })
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .exceptionHandling(exceptionHandling -> {
                    log.info("Configuring exception handling");
                    exceptionHandling
                            .authenticationEntryPoint((exchange, ex) -> {
                                if (exchange.getRequest().getHeaders().getAccept()
                                        .contains(MediaType.APPLICATION_JSON)) {
                                    log.warn("Unauthorized access attempt detected, returning 401");
                                    return Mono.fromRunnable(() ->
                                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                                }
                                log.warn("Unauthorized access attempt detected, redirecting to /login");
                                return Mono.fromRunnable(() -> {
                                    exchange.getResponse().setStatusCode(HttpStatus.FOUND);
                                    exchange.getResponse().getHeaders().setLocation(URI.create("/login"));
                                });
                            });
                })
                .build();
    }
}