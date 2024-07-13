package com.example.springboot.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.BudgetCtgyRepository;
import com.example.springboot.dao.SpendingRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.SpendingInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class SpendingController {
    
    private final BudgetCtgyRepository budgetCtgyRepository;
    private final UserRepository userRepository;
    private final SpendingRepository spendingRepository;

    @Autowired
    public SpendingController(BudgetCtgyRepository budgetCtgyRepository, UserRepository userRepository, SpendingRepository spendingRepository) {
        this.budgetCtgyRepository = budgetCtgyRepository;
        this.userRepository = userRepository;
        this.spendingRepository = spendingRepository;
    }
    
    @PostMapping("/sbs/i_s/spending")
    public ResponseEntity<SpendingInfo> addSpending(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody SpendingInfo spending) {
        UserInfo userInfo = userRepository.findByUsername(user.getName());
        spending.setUserInfo(userInfo);
        return ResponseEntity.ok(spendingRepository.save(spending));
    }

    @GetMapping("/sbs/i_s/spending")
    public ResponseEntity<SpendingInfo[]> getSpending(@AuthenticationPrincipal FirebaseUserDTO user) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());

        return ResponseEntity.ok().body(spendingRepository.findAllByUserInfo(userInfo));
    }
    
    @PatchMapping("/sbs/i_s/spending/{s_id}")
    public ResponseEntity<SpendingInfo> updateSpending(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("s_id") Long id, @RequestBody SpendingInfo spending) {
    	SpendingInfo existingSpending = spendingRepository.findFirstById(id);
    	if (existingSpending == null)
    		throw new RuntimeException("Spending not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != existingSpending.getUserInfo())
    		throw new RuntimeException("user does not own this spending");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(spending, existingSpending);

        existingSpending.setId(id);

        spendingRepository.save(existingSpending);
        spendingRepository.refresh(existingSpending);
        return ResponseEntity.ok(existingSpending);
    }
    
    @DeleteMapping("/sbs/i_s/spending/{s_id}")
    public ResponseEntity<SpendingInfo> deleteSpending(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("s_id") Long id) {
    	SpendingInfo spending = spendingRepository.findFirstById(id);
    	if (spending == null)
    		throw new RuntimeException("Spending not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != spending.getUserInfo())
    		throw new RuntimeException("user does not own this spending");
    	
    	spendingRepository.delete(spending);
    	return ResponseEntity.ok(spending);
    }
}
