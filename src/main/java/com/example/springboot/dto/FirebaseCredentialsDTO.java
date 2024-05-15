package com.example.springboot.dto;

import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirebaseCredentialsDTO {

    public enum CredentialType {
		ID_TOKEN, SESSION
	}

	private CredentialType type;
	private FirebaseToken decodedToken;
	private String idToken;
	private String session;

}
