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

@Service
public class FinancialGoalServiceImpl implements FinancialGoalService{
    
    @Autowired
    private GoalRepository goalRepository;

    public Page<GoalInfo> getGoals(UserInfo userInfo, String query, String sortBy, String sortOrder, int page, int rowsPerPage) {
        Pageable pageable = PageRequest.of(page, rowsPerPage, Sort.Direction.fromString(sortOrder), sortBy);
        return goalRepository.findAllByUserInfoAndTitleContainingIgnoreCase(userInfo, query, pageable);
    }

    public GoalInfo save(GoalInfo goal) {
        return goalRepository.save(goal);
    }

    public List<GoalInfo> saveAll(List<GoalInfo> goals) {
        return StreamSupport.stream(goalRepository.saveAll(goals).spliterator(), false)
                .collect(Collectors.toList());
    }

    public GoalInfo findById(Long id) {
        return goalRepository.findFirstById(id);
    }

    public void delete(Long id) {
        goalRepository.deleteById(id);
    }

    public List<GoalInfo> findAllByUserInfo(UserInfo userInfo) {
        return List.of(goalRepository.findAllByUserInfo(userInfo));
    }

    public Map<String, Object> getStatChart() {
        // Map<String, Object> response = new HashMap<>();

        // Map<String, Integer> capitalBuilding = new HashMap<>();
        // capitalBuilding.put("thisYear", 5000);
        // capitalBuilding.put("lastYear", 3000);

        // Map<String, Object> debtPayment = new HashMap<>();
        // debtPayment.put("thisYear", 3000);
        // debtPayment.put("lastYear", 2000);
        // List<Map<String, Object>> repayment = new ArrayList<>();
        // for (int i = 1; i <= 12; i++) {
        //     Map<String, Object> month = new HashMap<>();
        //     month.put("month", new DateFormatSymbols().getMonths()[i-1]);
        //     month.put("amount", i * 100);
        //     repayment.add(month);
        // }
        // debtPayment.put("repayment", repayment);

        // Map<String, Object> longTermExpense = new HashMap<>();
        // List<Map<String, Object>> expenses = new ArrayList<>();
        // for (int i = 1; i <= 12; i++) {
        //     Map<String, Object> month = new HashMap<>();
        //     month.put("month", new DateFormatSymbols().getMonths()[i-1]);
        //     month.put("amount", i * 100);
        //     expenses.add(month);
        // }
        // longTermExpense.put("expenses", expenses);

        // response.put("capitalBuilding", capitalBuilding);
        // response.put("debtPayment", debtPayment);
        // response.put("longTermExpense", longTermExpense);

        // return response;

        Map<String, Object> response = new HashMap<>();

        // Fetch data from the repository
        Double capitalBuildingThisYear = goalRepository.sumAmountByTypeAndYear("capital building", LocalDate.now().getYear());
        Double capitalBuildingLastYear = goalRepository.sumAmountByTypeAndYear("capital building", LocalDate.now().getYear() - 1);
        Double debtPaymentThisYear = goalRepository.sumAmountByTypeAndYear("debt payment", LocalDate.now().getYear());
        Double debtPaymentLastYear = goalRepository.sumAmountByTypeAndYear("debt payment", LocalDate.now().getYear() - 1);
        List<Map<String, Object>> debtPaymentRepayment = goalRepository.monthlyAmountByType("debt payment");
        List<Map<String, Object>> longTermExpenseExpenses = goalRepository.monthlyAmountByType("long term expense");

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
