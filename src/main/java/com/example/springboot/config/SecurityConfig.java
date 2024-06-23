package com.example.springboot.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

import com.example.springboot.properties.SecurityProperties;
import com.example.springboot.service.UserDetailsServiceImpl;
import com.example.springboot.web.filter.SecurityFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.Date;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;
    private final SecurityFilter tokenAuthenticationFilter;

    @Autowired
    public SecurityConfig(ObjectMapper objectMapper, SecurityProperties securityProperties, SecurityFilter tokenAuthenticationFilter) {
        this.objectMapper = objectMapper;
        this.securityProperties = securityProperties;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors ->
                    cors
                        .disable() // disable means that we are not using the default CORS configuration
                )
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> 
                    authorize
                        .requestMatchers(HttpMethod.GET,
                            "/api/v1/tours",
                            "/api/v1/tours/{tour_id}",
                            "/api/v1/tours/{tour_id}/reviews",
                            "/api/v1/files/{filename}"
                        ).permitAll()
                        .requestMatchers(
                            "/",
                            "/api/v1/register",
                            "/api/v1/login",
                            "/api/v1/refreshToken",
                            "/oauth2/**",
                            "/login/oauth2/**",
                            "/login",
                            "/api-docs/**",
                            "/api-docs-ui.html",
                            "/swagger-ui/index.html",
                            "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers("/api/v1/**")
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement -> 
                    sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling -> 
                    exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> { 
                            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            Map<String, Object> errorObject = new HashMap<>();
                            int errorCode = 401;
                            errorObject.put("message", "Unauthorized access of protected resource, invalid credentials");
                            errorObject.put("error", HttpStatus.UNAUTHORIZED);
                            errorObject.put("code", errorCode);
                            errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(errorCode);
                            response.getWriter().write(objectMapper.writeValueAsString(errorObject));
                        })
                )
                .build();
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}