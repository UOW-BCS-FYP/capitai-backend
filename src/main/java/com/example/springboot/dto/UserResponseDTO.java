package com.example.springboot.dto;

import java.util.Set;

import com.example.springboot.model.UploadedFileInfo;
import com.example.springboot.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {

    // Basic User Info
    private Long id;
    private String username;
    private Set<UserRole> roles;
    private String email;
    private String bio;
    private UploadedFileInfo profilePicture; 

    // OAuth2 User Info
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
    private String googleId;
    private String facebookId;

}
