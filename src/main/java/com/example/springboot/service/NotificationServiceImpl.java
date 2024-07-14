package com.example.springboot.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.NotificationRepository;
import com.example.springboot.model.NotificationInfo;
import com.example.springboot.model.UserInfo;
import com.google.cloud.storage.Acl.User;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @Override
    public List<NotificationInfo> getNotifications(UserInfo userInfo) {
        // Fetch notifications from the repository
        return StreamSupport.stream(notificationRepository.findAllByUserInfo(userInfo).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UserInfo userInfo, Long id) {
        // Delete the notification from the repository
        notificationRepository.deleteByIdAndUserInfo(id, userInfo);
    }
    
}
