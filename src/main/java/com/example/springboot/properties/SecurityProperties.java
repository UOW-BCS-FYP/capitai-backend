package com.example.springboot.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import com.mysql.cj.protocol.Security;

import lombok.Data;

@Component
@ConfigurationProperties("spring.security")
@Data
public class SecurityProperties {

	public SecurityProperties() {
		System.out.println("SecurityProperties Constructor");
		System.out.println(allowCredentials);
	}

    SecurityCookieProperties cookieProps;
	SecurityFirebaseProperties firebaseProps;

	boolean allowCredentials;
	List<String> allowedOrigins;
	List<String> allowedHeaders;
	List<String> exposedHeaders;
	List<String> allowedMethods;
	List<String> allowedPublicApis;
}
