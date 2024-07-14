package com.example.springboot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;

public interface BudgetCategoryService {
    
    public Page<BudgetCtgyInfo> getBudgetCategories(UserInfo userInfo, String query, Boolean isBill, Boolean isActivated, Integer min, Integer max, Pageable pageable);
    public BudgetCtgyInfo getBudgetCategory(UserInfo userInfo, Long id);
    public BudgetCtgyInfo getBudgetCategoryByTitle(UserInfo userInfo, String title);
    public BudgetCtgyInfo addBudgetCategory(BudgetCtgyInfo budgetCategory);
    public BudgetCtgyInfo updateBudgetCategory(UserInfo userInfo, Long id, BudgetCtgyInfo budgetCategory);
    public void deleteBudgetCategory(UserInfo userInfo, Long id);

}
