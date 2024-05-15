package com.example.springboot.properties;

import lombok.Data;

@Data
public class SecurityFirebaseProperties {
    int sessionExpiryInDays;
	String databaseUrl;
	boolean enableStrictServerSession;
	boolean enableCheckSessionRevoked;
	boolean enableLogoutEverywhere;
}
