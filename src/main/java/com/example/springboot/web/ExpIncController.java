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
import com.example.springboot.dao.ExpIncRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class ExpIncController {

    private final ExpIncRepository expIncRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpIncController(
    	ExpIncRepository expIncRepository,
        UserRepository userRepository
    ) {
        this.expIncRepository = expIncRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/sbs/plan/exp")
    public ResponseEntity<ExpIncInfo[]> getExpInc(@AuthenticationPrincipal FirebaseUserDTO user) {
        // get current user
        UserInfo userInfo = userRepository.findByUsername(user.getName());

        return ResponseEntity.ok().body(expIncRepository.findAllByUserInfo(userInfo));
    }

    @GetMapping("/sbs/plan/exp/{exp_id}")
    public ResponseEntity<ExpIncInfo> getExpIncById(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("exp_id") Long id) {
    	ExpIncInfo ctgy = expIncRepository.findFirstById(id);
    	if (ctgy == null)
    		throw new RuntimeException("Expected income not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != ctgy.getUserInfo())
    		throw new RuntimeException("user does not own this expected income");
    	
        return ResponseEntity.ok(ctgy);
    }
    
    @PostMapping("/sbs/plan/exp")
    public ResponseEntity<ExpIncInfo> createExpInc(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody ExpIncInfo exp) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        exp.setUserInfo(userInfo);
        return ResponseEntity.ok(expIncRepository.save(exp));
    }

    @PatchMapping("/sbs/plan/exp/{exp_id}")
    public ResponseEntity<ExpIncInfo> updateExpInc(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("exp_id") Long id, @RequestBody ExpIncInfo exp) {
    	ExpIncInfo existingExp = expIncRepository.findFirstById(id);
    	if (existingExp == null)
    		throw new RuntimeException("Expected income not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != existingExp.getUserInfo())
    		throw new RuntimeException("user does not own this budget category");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(exp, existingExp);

        existingExp.setId(id);

        expIncRepository.save(existingExp);
        expIncRepository.refresh(existingExp);
        return ResponseEntity.ok(existingExp);
    }
    
    @DeleteMapping("/sbs/plan/exp/{exp_id}")
    public ResponseEntity<ExpIncInfo> updateExpInc(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("exp_id") Long id) {
    	ExpIncInfo exp = expIncRepository.findFirstById(id);
    	if (exp == null)
    		throw new RuntimeException("Budget category not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != exp.getUserInfo())
    		throw new RuntimeException("user does not own this budget category");
    	
    	expIncRepository.delete(exp);
    	return ResponseEntity.ok(exp);
    }
}
