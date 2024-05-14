package com.example.springboot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.model.UserInfo;

public interface UserService {

    UserResponseDTO registerUser(UserRequestDTO userRequest);

    UserResponseDTO saveUser(UserRequestDTO userRequest);

    UserResponseDTO getUser();

    UserInfo getCurrentUserInfo();

    List<UserResponseDTO> getAllUser();

    UserResponseDTO uploadPicture(MultipartFile pictureFile);

}