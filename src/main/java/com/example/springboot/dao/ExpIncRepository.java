package com.example.springboot.dao;

import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.UserInfo;

public interface ExpIncRepository extends RefreshableCRUDRepository<ExpIncInfo, Long> {
	ExpIncInfo findFirstById(Long id);
	ExpIncInfo[] findAllByUserInfo(UserInfo userInfo);
}
