package com.example.springboot.service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.dao.RoleRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.UserRequestDTO;
import com.example.springboot.dto.UserResponseDTO;
import com.example.springboot.model.UploadedFileInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.UserRole;
import com.example.springboot.storage.StorageService;

@Service
public class AdminUserServiceImpl implements UserService {

    private final UserRepository userRepository;    
    private final RoleRepository roleRepository;
    private final StorageService storageService;
    
    @Autowired
    public AdminUserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, StorageService storageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.storageService = storageService;
    }

    ModelMapper modelMapper = new ModelMapper();

    // register user
    public UserResponseDTO registerUser(UserRequestDTO userRequest) {
        UserInfo user = modelMapper.map(userRequest, UserInfo.class);

        if (userRepository.findByEmail(userRequest.getEmail()) != null) {
            throw new RuntimeException("User already exists..!!");
        }
        
        UserRole userRole = roleRepository.findByName("Admin");
        user.setRoles(
            new HashSet<>(Arrays.asList(userRole))
        );

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        UserInfo savedUser = userRepository.save(user);
        userRepository.refresh(savedUser);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequest) {
        UserInfo savedUser = null;

        UserInfo user = modelMapper.map(userRequest, UserInfo.class);

        if (userRequest.getPassword() != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String rawPassword = userRequest.getPassword();
            String encodedPassword = encoder.encode(rawPassword);
            user.setPassword(encodedPassword);
        }

        if(userRequest.getId() != null) {
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if(oldUser != null){
                user.setEmail(null); // email is not updatable #1

                // map updated user to old user ignoring null values
                modelMapper.getConfiguration().setSkipNullEnabled(true);
                modelMapper.map(user, oldUser);

                oldUser.setId(user.getId());
                // oldUser.setPassword(user.getPassword());
                // oldUser.setEmail(user.getEmail());
                // oldUser.setBio(user.getBio());
                // oldUser.setUsername(user.getEmail());

                savedUser = userRepository.save(oldUser);
                userRepository.refresh(savedUser);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + userRequest.getId());
            }
        } else {
            //  user.setCreatedBy(currentUser);
            savedUser = userRepository.save(user);
        }
        userRepository.refresh(savedUser);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUser() {
        return modelMapper.map(getCurrentUserInfo(), UserResponseDTO.class);
    }

    @Override
    public UserInfo getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) authentication.getPrincipal();
        String emailFromAccessToken = userDetail.getUsername();
        return userRepository.findByEmail(emailFromAccessToken);
    }

    @Override
    public List<UserResponseDTO> getAllUser() {
        List<UserInfo> users = (List<UserInfo>) userRepository.findAll();
        Type setOfDTOsType = new TypeToken<List<UserResponseDTO>>(){}.getType();
        return modelMapper.map(users, setOfDTOsType);
    }

    @Override
    public UserResponseDTO uploadPicture(MultipartFile pictureFile) {
        UploadedFileInfo uploaded = storageService.store(pictureFile);
        UserInfo currentUser = getCurrentUserInfo();
        currentUser.setProfilePicture(uploaded);
        UserInfo savedUser = userRepository.save(currentUser);
        userRepository.refresh(savedUser);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

}
