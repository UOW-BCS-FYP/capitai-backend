package com.example.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;

public interface FinancialGoalService {

    public Page<GoalInfo> getGoals(UserInfo userInfo, String query, String sortBy, String sortOrder, int page, int rowsPerPage);
    public GoalInfo save(UserInfo userInfo, GoalInfo goal);
    // public List<GoalInfo> saveAll(UserInfo userInfo, List<GoalInfo> goals);
    public GoalInfo update(UserInfo userInfo, GoalInfo goal, Long id);
    public List<GoalInfo> updateAll(UserInfo userInfo, List<GoalInfo> goals);
    public GoalInfo findById(UserInfo userInfo, Long id);
    // public void delete(Long id);
    public void delete(UserInfo userInfo, Long id);
    public List<GoalInfo> findAllByUserInfo(UserInfo userInfo);
    public Map<String, Object> getStatChart(UserInfo userInfo);
}
