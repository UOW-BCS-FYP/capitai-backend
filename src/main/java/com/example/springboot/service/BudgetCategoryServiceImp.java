package com.example.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.BudgetCtgyRepository;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;

@Service
public class BudgetCategoryServiceImp implements BudgetCategoryService {
    
    @Autowired
    private BudgetCtgyRepository budgetCategoryRepository;

    @Override
    public Page<BudgetCtgyInfo> getBudgetCategories(UserInfo userInfo, String query, Boolean isBill, Boolean isActivated, Integer min, Integer max, Pageable pageable) {
        return budgetCategoryRepository.getBudgetCategories(userInfo, query, isBill, isActivated, min, max, pageable);
    }

    @Override
    public BudgetCtgyInfo getBudgetCategory(UserInfo userInfo, Long id) {
        return budgetCategoryRepository.findFirstByIdAndUserInfo(id, userInfo);
    }

    @Override
    public BudgetCtgyInfo getBudgetCategoryByTitle(UserInfo userInfo, String title) {
        return budgetCategoryRepository.findFirstByTitleAndUserInfo(title, userInfo);
    }

    @Override
    public BudgetCtgyInfo addBudgetCategory(BudgetCtgyInfo budgetCategory) {
        return budgetCategoryRepository.save(budgetCategory);
    }

    @Override
    public BudgetCtgyInfo updateBudgetCategory(UserInfo userInfo, Long id, BudgetCtgyInfo budgetCategory) {
        BudgetCtgyInfo existingBudgetCategory = budgetCategoryRepository.findFirstByIdAndUserInfo(id, userInfo);
        if (existingBudgetCategory == null) {
            throw new RuntimeException("Budget category not found");
        }
        existingBudgetCategory.setTitle(budgetCategory.getTitle());
        existingBudgetCategory.setBill(budgetCategory.isBill());
        existingBudgetCategory.setActivated(budgetCategory.isActivated());
        return budgetCategoryRepository.save(existingBudgetCategory);
    }

    @Override
    public void deleteBudgetCategory(UserInfo userInfo, Long id) {
        budgetCategoryRepository.deleteByIdAndUserInfo(id, userInfo);
    }

}
