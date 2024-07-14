package com.example.springboot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.GoalRepository;
import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;

import jakarta.transaction.Transactional;

@Service
public class FinancialGoalServiceImpl implements FinancialGoalService{
    
    @Autowired
    private GoalRepository goalRepository;

    public Page<GoalInfo> getGoals(UserInfo userInfo, String query, String sortBy, String sortOrder, int page, int rowsPerPage) {
        Pageable pageable = PageRequest.of(page, rowsPerPage, Sort.Direction.fromString(sortOrder), sortBy);
        return goalRepository.findAllByUserInfoAndTitleContainingIgnoreCase(userInfo, query, pageable);
    }

    public GoalInfo save(UserInfo userInfo, GoalInfo goal) {
        goal.setUserInfo(userInfo);
        return goalRepository.save(goal);
    }

    public GoalInfo update(UserInfo userInfo, GoalInfo goal, Long id) {
        GoalInfo existingGoal = goalRepository.findFirstByIdAndUserInfo(id, userInfo);
        if (existingGoal == null) {
            return null;
        }
        goal.setId(id);
        goal.setUserInfo(userInfo);
        return goalRepository.save(goal);
    }

    public List<GoalInfo> updateAll(UserInfo userInfo, List<GoalInfo> goals) {
        for (GoalInfo goal : goals) {
            GoalInfo existingGoal = goalRepository.findFirstByIdAndUserInfo(goal.getId(), userInfo);
            if (existingGoal == null) {
                return null;
            }
        }
        return StreamSupport.stream(goalRepository.saveAll(goals).spliterator(), false)
            .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UserInfo userInfo, Long id) {
        goalRepository.deleteByIdAndUserInfo(id, userInfo);
    }

    public GoalInfo findById(UserInfo userInfo, Long id) {
        return goalRepository.findFirstByIdAndUserInfo(id, userInfo);
    }

    public List<GoalInfo> findAllByUserInfo(UserInfo userInfo) {
        return List.of(goalRepository.findAllByUserInfo(userInfo));
    }

    public Map<String, Object> getStatChart(UserInfo userInfo) {
        Map<String, Object> response = new HashMap<>();

        // Fetch data from the repository
        Double capitalBuildingThisYear = goalRepository.sumAmountByTypeAndYearAndUserInfo("capital building", LocalDate.now().getYear(), userInfo);
        Double capitalBuildingLastYear = goalRepository.sumAmountByTypeAndYearAndUserInfo("capital building", LocalDate.now().getYear() - 1, userInfo);
        Double debtPaymentThisYear = goalRepository.sumAmountByTypeAndYearAndUserInfo("debt payment", LocalDate.now().getYear(), userInfo);
        Double debtPaymentLastYear = goalRepository.sumAmountByTypeAndYearAndUserInfo("debt payment", LocalDate.now().getYear() - 1, userInfo);
        List<Map<String, Object>> debtPaymentRepayment = goalRepository.monthlyAmountByTypeAndUserInfo("debt payment", userInfo);
        List<Map<String, Object>> longTermExpenseExpenses = goalRepository.monthlyAmountByTypeAndUserInfo("long term expense", userInfo);

        // Check if the values are null and replace with default values
        capitalBuildingThisYear = capitalBuildingThisYear != null ? capitalBuildingThisYear : 0;
        capitalBuildingLastYear = capitalBuildingLastYear != null ? capitalBuildingLastYear : 0;
        debtPaymentThisYear = debtPaymentThisYear != null ? debtPaymentThisYear : 0;
        debtPaymentLastYear = debtPaymentLastYear != null ? debtPaymentLastYear : 0;
        debtPaymentRepayment = debtPaymentRepayment != null ? debtPaymentRepayment : new ArrayList<>();
        longTermExpenseExpenses = longTermExpenseExpenses != null ? longTermExpenseExpenses : new ArrayList<>();

        // Build the response
        response.put("capitalBuilding", Map.of("thisYear", capitalBuildingThisYear, "lastYear", capitalBuildingLastYear));
        response.put("debtPayment", Map.of("thisYear", debtPaymentThisYear, "lastYear", debtPaymentLastYear, "repayment", debtPaymentRepayment));
        response.put("longTermExpense", Map.of("expenses", longTermExpenseExpenses));

        return response;
    }

}
