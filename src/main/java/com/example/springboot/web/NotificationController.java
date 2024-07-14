package com.example.springboot.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.dto.NotificationResponseDTO;
import com.example.springboot.model.NotificationInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.NotificationService;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/notif")
    public ResponseEntity<NotificationResponseDTO> getNotifications(@AuthenticationPrincipal FirebaseUserDTO user) {
        // Fetch notifications from the service
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        List<NotificationInfo> notifications = notificationService.getNotifications(userInfo);

        // Return the notifications
        return ResponseEntity.ok(new NotificationResponseDTO(notifications, notifications.size()));
    }

    @DeleteMapping("/notif/{id}")
    public ResponseEntity<?> deleteNotification(@AuthenticationPrincipal FirebaseUserDTO user, Long id) {
        // Delete the notification from the service
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        notificationService.delete(userInfo, id);

        // Return success response
        return ResponseEntity.ok().build();
    }

}
