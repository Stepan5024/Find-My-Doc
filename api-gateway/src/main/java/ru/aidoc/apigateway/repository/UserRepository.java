package ru.aidoc.apigateway.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidoc.apigateway.model.User;

import java.util.Optional;

/**
 * Репозиторий для сущности User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}