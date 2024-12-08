package ru.aidoc.apigaterway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleDTO> roles;
}