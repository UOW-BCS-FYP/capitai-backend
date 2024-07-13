package com.example.springboot.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "USERS")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;
    
    private String username; // equilavent to email
    
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
    
    @Column(unique = true)
    private String email;

    private String bio;
    
    // OAuth2 User Info
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
    private String googleId;
    private String facebookId;

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private UploadedFileInfo profilePicture;

}