package com.example.springboot.dao;

import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.SpendingInfo;
import com.example.springboot.model.UserInfo;

public interface SpendingRepository extends RefreshableCRUDRepository<SpendingInfo, Long> {
    SpendingInfo findFirstById(Long id);
    SpendingInfo[] findAllByUserInfo(UserInfo user);
    void deleteAllByUserInfo(UserInfo userInfo);
}
