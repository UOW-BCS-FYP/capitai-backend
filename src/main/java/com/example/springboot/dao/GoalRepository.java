package com.example.springboot.dao;

import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;

public interface GoalRepository extends RefreshableCRUDRepository<GoalInfo, Long> {
	GoalInfo findFirstById(Long id);
	GoalInfo[] findAllByUserInfo(UserInfo userInfo);
}
