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

import com.example.springboot.dao.ExpIncRepository;
import com.example.springboot.dao.IncomeRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.IncomeInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class IncomeController {
    
    private final ExpIncRepository expIncRepository;
    private final UserRepository userRepository;
    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeController(ExpIncRepository expIncRepository, UserRepository userRepository, IncomeRepository incomeRepository) {
        this.expIncRepository = expIncRepository;
        this.userRepository = userRepository;
        this.incomeRepository = incomeRepository;
    }
    
    @PostMapping("/sbs/i_s/income")
    public ResponseEntity<IncomeInfo> addSpending(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody IncomeInfo income) {
        UserInfo userInfo = userRepository.findByUsername(user.getName());
        income.setUserInfo(userInfo);
        return ResponseEntity.ok(incomeRepository.save(income));
    }

    @GetMapping("/sbs/i_s/income")
    public ResponseEntity<IncomeInfo[]> getSpending(@AuthenticationPrincipal FirebaseUserDTO user) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());

        return ResponseEntity.ok().body(incomeRepository.findAllByUserInfo(userInfo));
    }
    
    @PatchMapping("/sbs/i_s/income/{s_id}")
    public ResponseEntity<IncomeInfo> updateSpending(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("i_id") Long id, @RequestBody IncomeInfo income) {
    	IncomeInfo existingIncome = incomeRepository.findFirstById(id);
    	if (existingIncome == null)
    		throw new RuntimeException("Income not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != existingIncome.getUserInfo())
    		throw new RuntimeException("user does not own this income");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(income, existingIncome);

        existingIncome.setId(id);

        incomeRepository.save(existingIncome);
        incomeRepository.refresh(existingIncome);
        return ResponseEntity.ok(existingIncome);
    }
    
    @DeleteMapping("/sbs/i_s/income/{s_id}")
    public ResponseEntity<IncomeInfo> deleteSpending(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("i_id") Long id) {
    	IncomeInfo income = incomeRepository.findFirstById(id);
    	if (income == null)
    		throw new RuntimeException("Income not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != income.getUserInfo())
    		throw new RuntimeException("user does not own this income");
    	
    	incomeRepository.delete(income);
    	return ResponseEntity.ok(income);
    }
}
