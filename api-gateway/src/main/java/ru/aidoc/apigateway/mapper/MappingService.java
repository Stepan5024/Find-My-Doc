package ru.aidoc.apigateway.mapper;


import org.springframework.stereotype.Service;
import ru.aidoc.apigateway.dto.RoleDTO;
import ru.aidoc.apigateway.dto.UserDTO;
import ru.aidoc.apigateway.model.Role;
import ru.aidoc.apigateway.model.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для маппинга между сущностями и DTO.
 */
@Service
public class MappingService {

    public UserDTO toUserDTO(User user) {
        Set<RoleDTO> roles = user.getRoles().stream()
                .map(this::toRoleDTO)
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getPhoneNumber(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                roles
        );
    }

    public RoleDTO toRoleDTO(Role role) {
        return new RoleDTO(
                role.getRoleId(),
                role.getRoleName()
        );
    }

    // Добавьте методы обратного маппинга, если необходимо
}