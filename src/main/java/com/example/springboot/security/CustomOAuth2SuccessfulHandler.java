package com.example.springboot.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.springboot.dao.RoleRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.JwtResponseDTO;
import com.example.springboot.model.RefreshToken;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.UserRole;
import com.example.springboot.service.JwtService;
import com.example.springboot.service.RefreshTokenService;
import com.example.springboot.service.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomOAuth2SuccessfulHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        
        CustomOAuth2UserDetails oauth2User = (CustomOAuth2UserDetails)authentication.getPrincipal();
        oauth2User.printAllAttributes();
        String email = oauth2User.getEmail();
        System.out.println("Email: " + email);

        UserInfo userInfo = userRepository.findByUsername(email);
        UserRole userRoles = roleRepository.findByName("User");

        if(userInfo == null){
            userInfo = new UserInfo();
            userInfo.setUsername(email);
            userInfo.setPassword("");
            userInfo.setRoles(
                new HashSet<>(Arrays.asList(userRoles))
            );
            userInfo.setEmail(oauth2User.getEmail());
            userInfo.setName(oauth2User.getName());
            userInfo.setGivenName(oauth2User.getGivenName());
            userInfo.setFamilyName(oauth2User.getFamilyName());
            userInfo.setPicture(oauth2User.getPicture());
            userInfo.setLocale(oauth2User.getLocale());
            if (oauth2User.isGoogle()) {
                userInfo.setGoogleId(oauth2User.getId());
            } else if (oauth2User.isFacebook()) {
                userInfo.setFacebookId(oauth2User.getId());
            }
            userRepository.save(userInfo);
        }
        else {
            System.out.println("User already exists in database..!!");
        }

        // super.onAuthenticationSuccess(request, response, authentication);

        // handle for react app
        // redirect user back to frontend with jwt tokens
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfo.getUsername());
        String accessToken = jwtService.GenerateToken(userInfo.getUsername());
        response.sendRedirect("https://wanderwise.rlok.tech/#accessToken=" + accessToken + "&refreshToken=" + refreshToken.getToken());
    }
}
