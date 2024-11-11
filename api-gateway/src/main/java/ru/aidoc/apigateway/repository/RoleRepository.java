package ru.aidoc.apigateway.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aidoc.apigateway.model.Role;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для сущности Role.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
    Optional<Role> findByRoleName(String roleName);
}