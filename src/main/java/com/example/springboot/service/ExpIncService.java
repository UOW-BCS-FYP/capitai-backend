package com.example.springboot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.UserInfo;

public interface ExpIncService {
    Page<ExpIncInfo> getExpectedIncome(UserInfo userInfo, String query, Boolean isRegular, Boolean isActivated, Double min, Double max, Pageable pageable);
    ExpIncInfo addExpectedIncome(UserInfo userInfo, ExpIncInfo newExpInc);
    void deleteExpectedIncome(UserInfo userInfo, Long id);
    ExpIncInfo updateExpectedIncome(UserInfo userInfo, ExpIncInfo updatedExpInc, Long id);
}