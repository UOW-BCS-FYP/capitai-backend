package com.example.springboot.dao;


import org.springframework.stereotype.Repository;

import com.example.springboot.model.RefreshToken;
import com.example.springboot.model.UserInfo;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends RefreshableCRUDRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByToken(String token);
}
