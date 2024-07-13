package com.example.springboot.dao;

import com.example.springboot.model.MessageInfo;
import com.example.springboot.model.UserInfo;

public interface MessageRepository extends RefreshableCRUDRepository<MessageInfo, Long> {
    MessageInfo findFirstById(Long id);
    MessageInfo[] findAllByUserInfo(UserInfo user);
}
