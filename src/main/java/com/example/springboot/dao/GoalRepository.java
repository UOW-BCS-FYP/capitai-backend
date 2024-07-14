package com.example.springboot.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;

public interface GoalRepository extends RefreshableCRUDRepository<GoalInfo, Long> {
	GoalInfo findFirstByIdAndUserInfo(Long id, UserInfo userInfo);
	GoalInfo[] findAllByUserInfo(UserInfo userInfo);
	void deleteByIdAndUserInfo(Long id, UserInfo userInfo);
	void deleteAllByUserInfo(UserInfo userInfo);
	Page<GoalInfo> findAllByUserInfoAndTitleContainingIgnoreCase(UserInfo userInfo, String title, Pageable pageable);

	@Query("SELECT SUM(g.amount) FROM GoalInfo g WHERE g.type = :type AND EXTRACT(YEAR FROM g.deadline) = :year AND g.userInfo = :userInfo")
	Double sumAmountByTypeAndYearAndUserInfo(@Param("type") String type, @Param("year") int year, @Param("userInfo") UserInfo userInfo);

	@Query("SELECT new map(EXTRACT(MONTH FROM g.deadline) as month, SUM(g.amount) as amount) FROM GoalInfo g WHERE g.type = :type AND g.userInfo = :userInfo GROUP BY EXTRACT(MONTH FROM g.deadline)")
	List<Map<String, Object>> monthlyAmountByTypeAndUserInfo(@Param("type") String type, @Param("userInfo") UserInfo userInfo);
}
