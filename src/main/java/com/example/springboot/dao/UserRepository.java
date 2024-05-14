package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.UserInfo;

@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);
    public UserInfo findByEmail(String email);
    public UserInfo findFirstById(Long id);
    public UserInfo[] findAllById(Long id);
    // public UserInfo findByEmail(String email);
}