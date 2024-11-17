package ru.aidoc.apigateway.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.dto.AuthRequest;
import ru.aidoc.apigateway.dto.RegisterRequest;


/**
 * Контроллер для аутентификации и регистрации пользователей.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final WebClient webClient;

    public AuthController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Эндпоинт для входа пользователя.
     *
     * @param authRequest Данные для аутентификации.
     * @return JWT токен или ошибка аутентификации.
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@Valid @RequestBody AuthRequest authRequest) {
        return webClient.post()
                .uri("http://auth-server/auth/login")
                .bodyValue(authRequest)
                .retrieve()
                .toEntity(String.class);
    }

    /**
     * Эндпоинт для регистрации нового пользователя.
     *
     * @param registerRequest Данные для регистрации.
     * @return Успешный ответ или ошибка регистрации.
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return webClient.post()
                .uri("http://auth-server/auth/register")
                .bodyValue(registerRequest)
                .retrieve()
                .toEntity(String.class);
    }
}