package ru.aidoc.apigaterway.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запроса аутентификации пользователя.
 */
public record AuthRequest(
        @NotBlank(message = "Email не может быть пустым")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {}