package ru.aidoc.apigateway.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.aidoc.apigateway.dto.UserDTO;
import ru.aidoc.apigateway.model.User;
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RoleMapper.class} // Добавьте это, если есть маппинг ролей
)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    UserDTO toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    User toEntity(UserDTO userDTO);
}