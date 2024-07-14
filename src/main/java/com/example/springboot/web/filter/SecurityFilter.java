package com.example.springboot.web.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseCredentialsDTO;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.UserInfo;
import com.example.springboot.properties.SecurityProperties;
import com.example.springboot.service.SecurityService;
import com.example.springboot.service.UserService;
import com.example.springboot.util.CookieUtils;
import com.google.auth.Credentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.internal.GetAccountInfoResponse.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {
    
    private final SecurityProperties securityProperties;
    private final SecurityService securityService;
    private final CookieUtils cookieUtils;
    private final UserRepository userRepository;

    @Autowired
    public SecurityFilter(SecurityProperties securityProperties, SecurityService securityService, CookieUtils cookieUtils, UserRepository userRepository) {
        this.securityProperties = securityProperties;
        this.securityService = securityService;
        this.cookieUtils = cookieUtils;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        verifyToken(request);
        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request) {
        String session = null;
        FirebaseToken decodedToken = null;
        FirebaseCredentialsDTO.CredentialType type = null;
        boolean strictServerSessionEnabled = securityProperties.getFirebaseProps().isEnableStrictServerSession();
        logger.info("Strict Server Session Enabled: " + strictServerSessionEnabled);
        Cookie sessionCookie = cookieUtils.getCookie("session");
        logger.info("Session Cookie: " + sessionCookie);
        String token = securityService.getBearerToken(request);
        // logger.info(token);
        try {
            if (sessionCookie != null) {
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth
                    .getInstance()
                    .verifySessionCookie(
                        session,
                        securityProperties.getFirebaseProps().isEnableCheckSessionRevoked()
                    );
                type = FirebaseCredentialsDTO.CredentialType.SESSION;
            } else if (!strictServerSessionEnabled && token != null && !token.equalsIgnoreCase("undefined")) {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                type = FirebaseCredentialsDTO.CredentialType.ID_TOKEN;
            }
            
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            log.error("Firebase Exception: ", e.getLocalizedMessage());
        }
        FirebaseUserDTO user = firebaseTokenToUserDto(decodedToken);
        logger.info("User: " + user);
        if (user != null) {
            createUserInfoIfNotExists(user);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    new FirebaseCredentialsDTO(type, decodedToken, token, session), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private FirebaseUserDTO firebaseTokenToUserDto(FirebaseToken decodedToken) {
        FirebaseUserDTO user = null;
        if (decodedToken != null) {
            user = new FirebaseUserDTO();
            user.setUid(decodedToken.getUid());
            user.setName(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setPicture(decodedToken.getPicture());
            user.setIssuer(decodedToken.getIssuer());
            user.setEmailVerified(decodedToken.isEmailVerified());
        }
        return user;
    }

    private void createUserInfoIfNotExists(FirebaseUserDTO user) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        if (userInfo == null) {
            UserInfo newUser = new UserInfo();
            newUser.setEmail(user.getEmail());
            newUser.setName(user.getName());
            newUser.setPicture(user.getPicture());
            newUser.setUsername(user.getName());
            userRepository.save(newUser);
        }
    }

}
