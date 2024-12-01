package ru.aidoc.apigateway.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.dto.AuthRequest;
import ru.aidoc.apigateway.dto.AuthResponse;
import ru.aidoc.apigateway.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;


/**
 * Контроллер для аутентификации и регистрации пользователей.
 */

/**
 * Контроллер для аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final WebClient webClient;
    @Value("${webclient.auth-server-url}")
    private String authServerUrl;

    public AuthController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@RequestBody RegisterRequest registerRequest) {
        log.info("Attempting to register user with email: {}", registerRequest.email());

        return webClient.post()
                .uri(authServerUrl + "/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .exchangeToMono(response -> {
                    if (response.statusCode().is2xxSuccessful()) {
                        return response.bodyToMono(AuthResponse.class)
                                .map(authResponse -> {
                                    log.info("Successfully registered user: {}", registerRequest.email());
                                    return ResponseEntity.ok(authResponse);
                                });
                    } else {
                        return response.bodyToMono(String.class)
                                .flatMap(error -> {
                                    log.error("Registration failed with status {}: {}",
                                            response.statusCode(), error);
                                    return Mono.just(ResponseEntity
                                            .status(response.statusCode())
                                            .body(new AuthResponse(null, "Bearer", registerRequest.email(), null)));
                                });
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error during registration: ", e);
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new AuthResponse(null, "Bearer", registerRequest.email(), null)));
                });
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        log.info("Login attempt for user: {}", authRequest.email());

        return webClient.post()
                .uri(authServerUrl +"/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authRequest)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .map(response -> {
                    log.info("Login successful for user: {}", authRequest.email());
                    if (response.getToken() == null) {
                        log.error("Token not received from auth server for user: {}", authRequest.email());
                        throw new RuntimeException("Token not received from auth server");
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response);
                })
                .onErrorResume(e -> {
                    log.error("Login failed for user {}: {}", authRequest.email(), e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(new AuthResponse(null, "Bearer", authRequest.email(), null)));
                });
    }
}