package ru.aidoc.apigaterway.dto;

import lombok.Getter;

@Getter
public class UserProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;

    // Геттеры и сеттеры
}