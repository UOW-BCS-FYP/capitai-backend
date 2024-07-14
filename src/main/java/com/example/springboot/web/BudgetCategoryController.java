package com.example.springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.BudgetCategoryCreateDTO;
import com.example.springboot.dto.BudgetCategoryListResponseDTO;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.BudgetCategoryService;
import com.google.cloud.storage.Acl.User;

@RestController
@RequestMapping("/api/v1/smart-budgeting/budget-category")
public class BudgetCategoryController {
    
    @Autowired
    private BudgetCategoryService budgetCategoryService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<BudgetCategoryListResponseDTO> getBudgetCategories(@AuthenticationPrincipal FirebaseUserDTO user,
                                                                    @RequestParam(required = false) String query,
                                                                    @RequestParam(required = false) Boolean isBill,
                                                                    @RequestParam(required = false) Boolean isActivated,
                                                                    @RequestParam(required = false) Integer min,
                                                                    @RequestParam(required = false) Integer max,
                                                                    Pageable pageable) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        // return ResponseEntity.ok(budgetCategoryService.getBudgetCategories(userInfo, query, isBill, isActivated, min, max, pageable));
        Page<BudgetCtgyInfo> budgetCategories = budgetCategoryService.getBudgetCategories(userInfo, query, isBill, isActivated, min, max, pageable);
        return ResponseEntity.ok(new BudgetCategoryListResponseDTO(budgetCategories.getContent(), (int) budgetCategories.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<BudgetCtgyInfo> addBudgetCategory(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody BudgetCategoryCreateDTO budgetCategory) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        BudgetCtgyInfo budgetCtgyInfo = new BudgetCtgyInfo();
        budgetCtgyInfo.setTitle(budgetCategory.getTitle());
        budgetCategory.setAmount(budgetCategory.getAmount());
        budgetCtgyInfo.setBill(budgetCategory.isBill());
        budgetCtgyInfo.setIntervalMonth(budgetCategory.getIntervalMonth());
        budgetCtgyInfo.setActivated(budgetCategory.isActivated());
        budgetCtgyInfo.setUserInfo(userInfo);
        return ResponseEntity.ok(budgetCategoryService.addBudgetCategory(budgetCtgyInfo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetCtgyInfo> updateBudgetCategory(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id, @RequestBody BudgetCategoryCreateDTO budgetCategory) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        BudgetCtgyInfo budgetCtgyInfo = new BudgetCtgyInfo();
        budgetCtgyInfo.setTitle(budgetCategory.getTitle());
        budgetCategory.setAmount(budgetCategory.getAmount());
        budgetCtgyInfo.setBill(budgetCategory.isBill());
        budgetCtgyInfo.setIntervalMonth(budgetCategory.getIntervalMonth());
        budgetCtgyInfo.setActivated(budgetCategory.isActivated());
        return ResponseEntity.ok(budgetCategoryService.updateBudgetCategory(userInfo, id, budgetCtgyInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetCategory(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        budgetCategoryService.deleteBudgetCategory(userInfo, id);
        return ResponseEntity.ok().build();
    }
}