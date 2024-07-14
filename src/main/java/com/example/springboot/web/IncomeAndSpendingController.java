package com.example.springboot.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.dto.IncomeAndSpendingCreateDTO;
import com.example.springboot.dto.IncomeAndSpendingListResponseDTO;
import com.example.springboot.dto.IncomeAndSpendingResponseDTO;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.IncomeAndSpendingInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.BudgetCategoryService;
import com.example.springboot.service.IncomeAndSpendingService;

@RestController
@RequestMapping("/api/v1/smart-budgeting/income-spending-record")
public class IncomeAndSpendingController {
    
    @Autowired
    private IncomeAndSpendingService incomeAndSpendingService;
    @Autowired
    private BudgetCategoryService budgetCategoryService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<IncomeAndSpendingListResponseDTO> getAllInSInfo(
        @AuthenticationPrincipal FirebaseUserDTO user,
        @RequestParam(defaultValue = "") String query,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortOrder,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int rowsPerPage,
        @RequestParam(required = false) Date dateStart,
        @RequestParam(required = false) Date dateEnd,
        @RequestParam(required = false) Boolean isIncome,
        @RequestParam(required = false) Double min,
        @RequestParam(required = false) Double max
    ) {
        // Implement filtering, sorting, and pagination logic here
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        PageRequest pageRequest = PageRequest.of(page, rowsPerPage, Sort.Direction.fromString(sortOrder), sortBy);
        Page<IncomeAndSpendingInfo> pageResult = incomeAndSpendingService.getAllInSInfo(userInfo, query, dateStart, dateEnd, isIncome, min, max, pageRequest);
        List<IncomeAndSpendingInfo> inSInfoList = pageResult.getContent();
        return ResponseEntity.ok().body(new IncomeAndSpendingListResponseDTO(
            inSInfoList.stream().map(inSInfo -> new IncomeAndSpendingResponseDTO(inSInfo.getId(), inSInfo.getTitle(), inSInfo.getAmount(), inSInfo.getDate(), inSInfo.getSubject(), inSInfo.isIncome(), inSInfo.getCategory().getTitle())).collect(Collectors.toList()),
            (int) pageResult.getTotalElements()
        ));
    }

    @PostMapping
    public ResponseEntity<IncomeAndSpendingResponseDTO> saveInSInfo(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody IncomeAndSpendingCreateDTO inSInfo) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        IncomeAndSpendingInfo newInSInfo = new IncomeAndSpendingInfo();
        newInSInfo.setTitle(inSInfo.getTitle());
        newInSInfo.setAmount(inSInfo.getAmount());
        newInSInfo.setDate(inSInfo.getDate());
        newInSInfo.setSubject(inSInfo.getSubject());
        newInSInfo.setIncome(inSInfo.isIncome());
        BudgetCtgyInfo category = budgetCategoryService.getBudgetCategoryByTitle(userInfo, inSInfo.getCategory());
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        newInSInfo.setCategory(category);
        newInSInfo = incomeAndSpendingService.saveInSInfo(userInfo, newInSInfo);
        IncomeAndSpendingResponseDTO dto = new IncomeAndSpendingResponseDTO(newInSInfo.getId(), newInSInfo.getTitle(), newInSInfo.getAmount(), newInSInfo.getDate(), newInSInfo.getSubject(), newInSInfo.isIncome(), newInSInfo.getCategory().getTitle());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteInSInfo(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        incomeAndSpendingService.deleteInSInfo(userInfo, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeAndSpendingResponseDTO> updateInSInfo(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id, @RequestBody IncomeAndSpendingCreateDTO inSInfo) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        IncomeAndSpendingInfo updatedInSInfo = new IncomeAndSpendingInfo();
        updatedInSInfo.setTitle(inSInfo.getTitle());
        updatedInSInfo.setAmount(inSInfo.getAmount());
        updatedInSInfo.setDate(inSInfo.getDate());
        updatedInSInfo.setSubject(inSInfo.getSubject());
        updatedInSInfo.setIncome(inSInfo.isIncome());
        BudgetCtgyInfo category = budgetCategoryService.getBudgetCategoryByTitle(userInfo, inSInfo.getCategory());
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        updatedInSInfo.setCategory(category);
        updatedInSInfo = incomeAndSpendingService.updateInSInfo(userInfo, id, updatedInSInfo);
        return ResponseEntity.ok().body(new IncomeAndSpendingResponseDTO(updatedInSInfo.getId(), updatedInSInfo.getTitle(), updatedInSInfo.getAmount(), updatedInSInfo.getDate(), updatedInSInfo.getSubject(), updatedInSInfo.isIncome(), updatedInSInfo.getCategory().getTitle()));
    }

    @GetMapping("/stat-chart")
    public ResponseEntity<Map<String, Object>> getStatChart(@AuthenticationPrincipal FirebaseUserDTO user) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        return ResponseEntity.ok(incomeAndSpendingService.getStatChart(userInfo));
    }
}
