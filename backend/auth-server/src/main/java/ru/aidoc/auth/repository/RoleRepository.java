package ru.aidoc.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidoc.auth.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(String roleName);
}