package com.example.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.ExpIncRepository;
import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.UserInfo;

@Service
public class ExpIncServiceImp implements ExpIncService {

    @Autowired
    private ExpIncRepository expIncRepository;

    @Override
    public Page<ExpIncInfo> getExpectedIncome(UserInfo userInfo, String query, Boolean isRegular, Boolean isActivated, Double min, Double max, Pageable pageable) {
        return expIncRepository.getExpectedIncome(userInfo, query, isRegular, isActivated, min, max, pageable);
    }

    @Override
    public ExpIncInfo addExpectedIncome(UserInfo userInfo, ExpIncInfo newExpInc) {
        newExpInc.setUserInfo(userInfo);
        return expIncRepository.save(newExpInc);
    }

    @Override
    public void deleteExpectedIncome(UserInfo userInfo, Long id) {
        expIncRepository.deleteByIdAndUserInfo(id, userInfo);
    }

    @Override
    public ExpIncInfo updateExpectedIncome(UserInfo userInfo, ExpIncInfo updatedExpInc, Long id) {
        ExpIncInfo expInc = expIncRepository.findFirstByIdAndUserInfo(id, userInfo);
        if (expInc == null) {
            throw new IllegalArgumentException("Expected income not found");
        }
        updatedExpInc.setId(id);
        updatedExpInc.setUserInfo(userInfo);
        return expIncRepository.save(updatedExpInc);
    }
}