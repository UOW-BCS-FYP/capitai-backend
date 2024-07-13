package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDTO {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String bio;

    // private String name;
    // private String givenName;
    // private String familyName;
    // private String picture;
    // private String locale;
    // private Set<UserRole> roles;

}