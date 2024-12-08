package ru.aidoc.apigaterway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * DTO для запроса регистрации нового пользователя.
 */

public record RegisterRequest(
        @Email(message = "Email должен быть валидным")
        @NotBlank(message = "Email не может быть пустым")
        String email,

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
        String password,

        String firstName,
        String lastName,
        String middleName,
        String phoneNumber
) {}