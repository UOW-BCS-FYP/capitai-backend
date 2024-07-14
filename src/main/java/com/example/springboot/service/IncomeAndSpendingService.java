package com.example.springboot.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.springboot.model.IncomeAndSpendingInfo;
import com.example.springboot.model.UserInfo;

public interface IncomeAndSpendingService {

    public List<IncomeAndSpendingInfo> getAllInSInfo(UserInfo userInfo);
    public Page<IncomeAndSpendingInfo> getAllInSInfo(UserInfo userInfo, String searchQuery, Date dateStart, Date dateEnd, Boolean isIncome, Double min, Double max, PageRequest pageRequest);
    public IncomeAndSpendingInfo saveInSInfo(UserInfo userInfo, IncomeAndSpendingInfo inSInfo);
    public void deleteInSInfo(UserInfo userInfo, Long id);
    public IncomeAndSpendingInfo updateInSInfo(UserInfo userInfo, Long id, IncomeAndSpendingInfo inSInfo);
    public Map<String, Object> getStatChart(UserInfo userInfo);
}
