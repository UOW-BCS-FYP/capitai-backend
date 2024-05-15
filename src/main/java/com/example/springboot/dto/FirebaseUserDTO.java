package com.example.springboot.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FirebaseUserDTO implements Serializable {

    private static final long serialVersionUID = 4408418647685225829L;
	private String uid;
	private String name;
	private String email;
	private boolean isEmailVerified;
	private String issuer;
	private String picture;

}
