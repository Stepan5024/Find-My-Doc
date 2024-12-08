package ru.aidoc.apigaterway.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private String email;
    private String role;

    public AuthResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }
}