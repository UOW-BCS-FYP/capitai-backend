package com.example.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.dto.AuthRequestDTO;
import com.example.springboot.dto.JwtResponseDTO;
import com.example.springboot.dto.RefreshTokenRequestDTO;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.model.RefreshToken;
import com.example.springboot.model.TourInfo;
import com.example.springboot.service.AdminUserServiceImpl;
import com.example.springboot.service.JwtService;
import com.example.springboot.service.RefreshTokenService;
import com.example.springboot.service.UserService;
import com.example.springboot.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserService adminUserService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserServiceImpl userService, AdminUserServiceImpl adminUserService, JwtService jwtService, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.adminUserService = adminUserService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequest) {
        try {
            UserResponseDTO userResponse = userService.registerUser(userRequest);
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponseDTO> userResponses = adminUserService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    // @GetMapping("/test")
    // public String test() {
    //     try {
    //         return "Welcome";
    //     } catch (Exception e){
    //         throw new RuntimeException(e);
    //     }
    // }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        System.out.println("Authentication: " + authentication.isAuthenticated());
        if(authentication.isAuthenticated()){
            System.out.println("User Authenticated..!!");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getEmail());
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getEmail()))
                    .refreshToken(refreshToken.getToken()).build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDTO.getRefreshToken());
        return ResponseEntity.ok("Refresh Token Deleted Successfully..!!");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(){
        UserResponseDTO userResponse = userService.getUser();
        return ResponseEntity.ok().body(userResponse);
    }

    // @PatchMapping(path = "/me", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    // public ResponseEntity<UserResponseDTO> me(@RequestBody UserRequestDTO userRequest, @RequestParam("picture") MultipartFile picture) {
    @PatchMapping("/me")
    public ResponseEntity<UserResponseDTO> me(@RequestBody UserRequestDTO userRequest) {
        UserResponseDTO user = userService.getUser();
        // if (userRequest.getId() == null) {
        //     return ResponseEntity.badRequest();   
        // }
        userRequest.setId(user.getId());
        UserResponseDTO userResponse = userService.saveUser(userRequest);
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping(path = "/me/upload/picture", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<UserResponseDTO> uploadPicture(@RequestPart("picture") MultipartFile picture) {
        UserResponseDTO userResponse = userService.uploadPicture(picture);
        return ResponseEntity.ok().body(userResponse);
    }
}