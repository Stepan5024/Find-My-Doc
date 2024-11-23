package ru.aibok.auth.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private UUID roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    public Role(String roleName) {
        this.roleId = UUID.randomUUID(); // Генерируем UUID при создании
        this.roleName = roleName;
    }
}