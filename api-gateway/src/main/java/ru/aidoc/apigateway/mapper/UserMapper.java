package ru.aidoc.apigateway.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.aidoc.apigateway.dto.RoleDTO;
import ru.aidoc.apigateway.dto.UserDTO;
import ru.aidoc.apigateway.model.Role;
import ru.aidoc.apigateway.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    RoleDTO roleToRoleDTO(Role role);

    // Добавьте методы обратного маппинга, если необходимо
}