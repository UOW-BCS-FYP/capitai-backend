package com.example.springboot.dao;

import java.util.List;

import com.example.springboot.model.NotificationInfo;
import com.example.springboot.model.UserInfo;

public interface NotificationRepository extends RefreshableCRUDRepository<NotificationInfo, Long> {
    
    NotificationInfo findFirstById(Long id);
    List<NotificationInfo> findAllByUserInfo(UserInfo userInfo);
    void deleteById(Long id);
    void deleteAllByUserInfo(UserInfo userInfo);
    void deleteByIdAndUserInfo(Long id, UserInfo userInfo);
}
