package ru.aidoc.apigateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для запроса аутентификации пользователя.
 */
public record AuthRequest(
        @Email(message = "Email должен быть валидным")
        @NotBlank(message = "Email не может быть пустым")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        String password
) {}