package com.example.springboot.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.springboot.security.CustomOAuth2UserDetails;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService.loadUser");
        OAuth2User user = super.loadUser(userRequest);
        System.out.println("CustomOAuth2UserService.loadUser user=" + user);
        return new CustomOAuth2UserDetails(user, userRequest.getClientRegistration().getClientName());
    }
}
