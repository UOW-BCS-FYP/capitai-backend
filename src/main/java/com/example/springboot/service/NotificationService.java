package com.example.springboot.service;

import java.util.List;

import com.example.springboot.model.NotificationInfo;
import com.example.springboot.model.UserInfo;

public interface NotificationService {
    
    public List<NotificationInfo> getNotifications(UserInfo userInfo);
    public void delete(UserInfo userInfo, Long id);

}
