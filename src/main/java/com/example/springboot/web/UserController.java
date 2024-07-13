package com.example.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.service.AdminUserServiceImpl;
import com.example.springboot.service.UserService;
import com.example.springboot.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final UserService adminUserService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserServiceImpl userService, AdminUserServiceImpl adminUserService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.adminUserService = adminUserService;
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

    @GetMapping("/me")
    // public ResponseEntity<UserResponseDTO> me(){
    //     UserResponseDTO userResponse = userService.getUser();
    //     return ResponseEntity.ok().body(userResponse);
    // }
    public ResponseEntity<FirebaseUserDTO> getUserInfo(@AuthenticationPrincipal FirebaseUserDTO user) {
        return ResponseEntity.ok(user);
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