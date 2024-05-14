package com.example.springboot.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2UserDetails implements OAuth2User {

    // constants
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_USERNAME = "username";
    public static final String ATTR_PICTURE = "picture";
    public static final String ATTR_GIVEN_NAME = "given_name";
    public static final String ATTR_FAMILY_NAME = "family_name";
    public static final String ATTR_LOCALE = "locale";
    public static final String ATTR_ID = "sub";

    private OAuth2User oAuth2User;
    private String OAuth2Client;

    public CustomOAuth2UserDetails(OAuth2User oAuth2User, String OAuth2Client) {
        this.oAuth2User = oAuth2User;
        this.OAuth2Client = OAuth2Client;
    }

    public boolean isGoogle() {
        return OAuth2Client.equals("Google");
    }

    public boolean isFacebook() {
        return OAuth2Client.equals("Facebook");
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute(ATTR_NAME);
    }

    public String getOAuth2Client() {
        return OAuth2Client;
    }

    public String getEmail() {
        return oAuth2User.getAttribute(ATTR_EMAIL);
    }

    public String getPicture() {
        return oAuth2User.getAttribute(ATTR_PICTURE);
    }

    public String getGivenName() {
        return oAuth2User.getAttribute(ATTR_GIVEN_NAME);
    }

    public String getFamilyName() {
        return oAuth2User.getAttribute(ATTR_FAMILY_NAME);
    }

    public String getLocale() {
        return oAuth2User.getAttribute(ATTR_LOCALE);
    }

    public String getId() {
        return oAuth2User.getAttribute(ATTR_ID);
    }

    @Override
    public String getAttribute(String name) {
        return oAuth2User.getAttribute(name);
    }

    @Override
    public java.util.Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    public void printAllAttributes() {
        System.out.println("CustomOAuth2UserDetails.printAllAttributes");
        for (String key : oAuth2User.getAttributes().keySet()) {
            System.out.println(key + " = " + oAuth2User.getAttribute(key));
        }
    }

}
