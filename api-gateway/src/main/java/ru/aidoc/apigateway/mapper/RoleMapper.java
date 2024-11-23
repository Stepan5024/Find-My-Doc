package ru.aidoc.apigateway.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.aidoc.apigateway.dto.RoleDTO;
import ru.aidoc.apigateway.model.Role;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {
    RoleDTO toDto(Role role);
    Role toEntity(RoleDTO roleDTO);
}