package ru.aidoc.apigateway.dto;


import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * DTO для передачи информации о пользователе.
 */
@Getter
public record UserDTO(
        UUID userId,
        String email,
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<RoleDTO> roles
) {}