package ru.aibok.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.aibok.auth.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(String roleName);
}