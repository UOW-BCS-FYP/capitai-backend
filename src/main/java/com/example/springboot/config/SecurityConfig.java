package com.example.springboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.springboot.security.CustomOAuth2SuccessfulHandler;
import com.example.springboot.service.CustomOAuth2UserService;
import com.example.springboot.service.UserDetailsServiceImpl;
import com.example.springboot.web.filter.JwtAuthFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    CustomOAuth2UserService oAuth2UserService;

    @Autowired
    CustomOAuth2SuccessfulHandler customOAuth2SuccessfulHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .oauth2Login(oauth2 -> 
                    oauth2
                        .userInfoEndpoint(userInfo -> 
                            userInfo
                                .userService(oAuth2UserService)
                        )
                        .successHandler(customOAuth2SuccessfulHandler)
                )
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
                // .sessionManagement(sessionManagement -> 
                //     sessionManagement
                //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> 
                    exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> 
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(
            new SecurityRequirement()
                .addList("Bearer Authentication"))
                .components(new Components()
                    .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
            // .info(new Info().title("My REST API")
            //     .description("Some custom description of API.")
            //     .version("1.0").contact(new Contact().name("Sallo Szrajbman")
            //         .email("www.baeldung.com").url("salloszraj@gmail.com"))
            //     .license(new License().name("License of API")
            //         .url("API license URL")));
    }
}