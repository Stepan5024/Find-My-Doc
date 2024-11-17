package ru.aidoc.apigateway.dto;

import java.util.UUID;

/**
 * DTO для передачи информации о роли.
 */
public record RoleDTO(
        UUID roleId,
        String roleName
) {}