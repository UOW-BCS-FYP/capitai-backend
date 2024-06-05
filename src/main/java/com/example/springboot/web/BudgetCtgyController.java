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
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class BudgetCtgyController {

    private final BudgetCtgyRepository budgetCtgyRepository;
    private final UserRepository userRepository;

    @Autowired
    public BudgetCtgyController(
    	BudgetCtgyRepository budgetCtgyRepository,
        UserRepository userRepository
    ) {
        this.budgetCtgyRepository = budgetCtgyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/sbs/plan/bctgy")
    public ResponseEntity<BudgetCtgyInfo[]> getBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user) {
        // get current user
        UserInfo userInfo = userRepository.findByUsername(user.getName());

        return ResponseEntity.ok().body(budgetCtgyRepository.findAllByUserInfo(userInfo));
    }

    @GetMapping("/sbs/plan/bctgy/{bctgy_id}")
    public ResponseEntity<BudgetCtgyInfo> getBudgetCtgyById(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("bctgy_id") Long id) {
    	BudgetCtgyInfo ctgy = budgetCtgyRepository.findFirstById(id);
    	if (ctgy == null)
    		throw new RuntimeException("Budget category not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != ctgy.getUserInfo())
    		throw new RuntimeException("user does not own this budget category");
    	
        return ResponseEntity.ok(ctgy);
    }
    
    @PostMapping("/sbs/plan/bctgy")
    public ResponseEntity<BudgetCtgyInfo> createBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody BudgetCtgyInfo ctgy) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        ctgy.setUserInfo(userInfo);
        return ResponseEntity.ok(budgetCtgyRepository.save(ctgy));
    }

    @PatchMapping("/sbs/plan/bctgy/{bctgy_id}")
    public ResponseEntity<BudgetCtgyInfo> updateBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("bctgy_id") Long id, @RequestBody BudgetCtgyInfo ctgy) {
    	BudgetCtgyInfo existingCtgy = budgetCtgyRepository.findFirstById(id);
    	if (existingCtgy == null)
    		throw new RuntimeException("Budget category not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != existingCtgy.getUserInfo())
    		throw new RuntimeException("user does not own this budget category");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(ctgy, existingCtgy);

        existingCtgy.setId(id);

        budgetCtgyRepository.save(existingCtgy);
        budgetCtgyRepository.refresh(existingCtgy);
        return ResponseEntity.ok(existingCtgy);
    }
    
    @DeleteMapping("/sbs/plan/bctgy/{bctgy_id}")
    public ResponseEntity<BudgetCtgyInfo> updateBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("bctgy_id") Long id) {
    	BudgetCtgyInfo ctgy = budgetCtgyRepository.findFirstById(id);
    	if (ctgy == null)
    		throw new RuntimeException("Budget category not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != ctgy.getUserInfo())
    		throw new RuntimeException("user does not own this budget category");
    	
    	budgetCtgyRepository.delete(ctgy);
    	return ResponseEntity.ok(ctgy);
    }
}
