package ru.aidoc.apigateway.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO для запроса запуска проверки документов.
 */
public record CheckRequest(
        @NotBlank(message = "Document ID не может быть пустым")
        String documentId,

        @NotNull(message = "Список проверок не может быть null")
        List<@NotBlank(message = "Название проверки не может быть пустым") String> checks
) {}