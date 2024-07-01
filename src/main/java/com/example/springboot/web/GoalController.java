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
import com.example.springboot.dao.GoalRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;

@RestController
@RequestMapping("/api/v1/")
public class GoalController {
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Autowired
    public GoalController(
    	GoalRepository goalRepository,
        UserRepository userRepository
    ) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/goal")
    public ResponseEntity<GoalInfo[]> getGoal(@AuthenticationPrincipal FirebaseUserDTO user) {
        // get current user
        UserInfo userInfo = userRepository.findByUsername(user.getName());
        return ResponseEntity.ok().body(goalRepository.findAllByUserInfo(userInfo));
    }

    @GetMapping("/goal/{g_id}")
    public ResponseEntity<GoalInfo> getGoalByID(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("g_id") Long id) {
    	GoalInfo goal = goalRepository.findFirstById(id);
    	if (goal == null)
    		throw new RuntimeException("Goal not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != goal.getUserInfo())
    		throw new RuntimeException("user does not own this goal");
    	
        return ResponseEntity.ok(goal);
    }
    
    @PostMapping("/goal")
    public ResponseEntity<GoalInfo> createBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody GoalInfo goal) {
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
        goal.setUserInfo(userInfo);
        return ResponseEntity.ok(goalRepository.save(goal));
    }

    @PatchMapping("/goal/{g_id}")
    public ResponseEntity<GoalInfo> updateBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("g_id") Long id, @RequestBody GoalInfo goal) {
    	GoalInfo existingGoal = goalRepository.findFirstById(id);
    	if (existingGoal == null)
    		throw new RuntimeException("Goal not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != existingGoal.getUserInfo())
    		throw new RuntimeException("user does not own this goal");

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(goal, existingGoal);

        existingGoal.setId(id);

        goalRepository.save(existingGoal);
        goalRepository.refresh(existingGoal);
        return ResponseEntity.ok(existingGoal);
    }
    
    @DeleteMapping("/goal/{g_id}")
    public ResponseEntity<GoalInfo> updateBudgetCtgy(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable("g_id") Long id) {
    	GoalInfo goal = goalRepository.findFirstById(id);
    	if (goal == null)
    		throw new RuntimeException("Goal not found");
    	
    	UserInfo userInfo = userRepository.findByUsername(user.getName());
    	if (userInfo != goal.getUserInfo())
    		throw new RuntimeException("user does not own this goal");
    	
        goalRepository.delete(goal);
    	return ResponseEntity.ok(goal);
    }
}
