package com.example.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.MockDataService;

@RestController
@RequestMapping("/api/v1/mock")
public class MockDataController {

    @Autowired
    private MockDataService mockDataService;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/all-data")
    public ResponseEntity<String> generateAllData(@AuthenticationPrincipal FirebaseUserDTO user) {
        // Generate all data
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        mockDataService.generateAllData(userInfo);
        return ResponseEntity.ok("All data generated");
    }

    @DeleteMapping("/all-data")
    public ResponseEntity<String> deleteAllData(@AuthenticationPrincipal FirebaseUserDTO user) {
        // Delete all data
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        mockDataService.deleteAllData(userInfo);
        return ResponseEntity.ok("All data deleted");
    }
}
