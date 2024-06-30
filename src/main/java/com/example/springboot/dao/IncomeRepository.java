package com.example.springboot.dao;

import com.example.springboot.model.IncomeInfo;
import com.example.springboot.model.UserInfo;

public interface IncomeRepository extends RefreshableCRUDRepository<IncomeInfo, Long> {
    IncomeInfo findFirstById(Long id);
    IncomeInfo[] findAllByUserInfo(UserInfo user);
}
