package com.example.springboot.properties;

import lombok.Data;

@Data
public class SecurityCookieProperties {
    String domain;
	String path;
	boolean httpOnly;
	boolean secure;
	int maxAgeInMinutes;
}
