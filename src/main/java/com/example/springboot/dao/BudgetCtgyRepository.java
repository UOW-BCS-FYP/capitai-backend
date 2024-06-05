package com.example.springboot.dao;

import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;

public interface BudgetCtgyRepository extends RefreshableCRUDRepository<BudgetCtgyInfo, Long> {
	BudgetCtgyInfo findFirstById(Long id);
	BudgetCtgyInfo[] findAllByUserInfo(UserInfo userInfo);
}
