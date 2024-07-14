package com.example.springboot.web;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dao.GoalRepository;
import com.example.springboot.dao.UserRepository;
import com.example.springboot.dto.FinancialGoalCreateDTO;
import com.example.springboot.dto.FinancialGoalListResponseDTO;
import com.example.springboot.dto.FirebaseUserDTO;
import com.example.springboot.model.GoalInfo;
import com.example.springboot.model.UserInfo;
import com.example.springboot.service.FinancialGoalService;

@RestController
@RequestMapping("/api/v1/goal-tracker")
public class GoalController {
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    @Autowired
    private FinancialGoalService goalService;

    @Autowired
    public GoalController(
    	GoalRepository goalRepository,
        UserRepository userRepository
    ) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<FinancialGoalListResponseDTO> getGoals(
        @AuthenticationPrincipal FirebaseUserDTO user,
        @RequestParam(required = false, defaultValue = "") String query,
        @RequestParam(required = false, defaultValue = "id") String sortBy,
        @RequestParam(required = false, defaultValue = "asc") String sortOrder,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int rowsPerPage
    ) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        // return goalService.getGoals(userInfo, query, sortBy, sortOrder, page, rowsPerPage);
        Page<GoalInfo> goals = goalService.getGoals(userInfo, query, sortBy, sortOrder, page, rowsPerPage);
        return ResponseEntity.ok(new FinancialGoalListResponseDTO(goals.getContent(), (int) goals.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<GoalInfo> addGoal(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody FinancialGoalCreateDTO newGoalDTO) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        GoalInfo newGoal = new GoalInfo();
        newGoal.setTitle(newGoalDTO.getTitle());
        newGoal.setType(newGoalDTO.getType());
        newGoal.setAmount(newGoalDTO.getAmount());
        newGoal.setDeadline(newGoalDTO.getDeadline());
        newGoal.setPriority(newGoalDTO.getPriority());
        newGoal.setUserInfo(userInfo);
        newGoal.setCompleted(false);
        GoalInfo savedGoal = goalService.save(userInfo, newGoal);
        return ResponseEntity.ok(savedGoal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalInfo> getGoal(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        return ResponseEntity.ok(goalService.findById(userInfo, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalInfo> updateGoal(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id, @RequestBody GoalInfo updatedGoal) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        return ResponseEntity.ok(goalService.update(userInfo, updatedGoal, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteGoal(@AuthenticationPrincipal FirebaseUserDTO user, @PathVariable Long id) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        goalService.delete(userInfo, id);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/rearrange")
    public ResponseEntity<FinancialGoalListResponseDTO> rearrangeGoals(@AuthenticationPrincipal FirebaseUserDTO user, @RequestBody GoalInfo updatedGoal) {
        UserInfo userInfo = userRepository.findByUsername(user.getName());
        List<GoalInfo> goals = goalService.findAllByUserInfo(userInfo);
        GoalInfo goalToRearrange = goals.stream().filter(goal -> goal.getId() == updatedGoal.getId()).findFirst().orElse(null);
        if (goalToRearrange == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        int oldPriority = goalToRearrange.getPriority();
        int newPriority = updatedGoal.getPriority();
        if (oldPriority == newPriority) {
            return ResponseEntity.ok(new FinancialGoalListResponseDTO(goals, goals.size()));
        }
        int direction = oldPriority < newPriority ? 1 : -1;
        goals.forEach(goal -> {
            if (goal.getId() == updatedGoal.getId()) return;
            if (direction == 1 && goal.getPriority() > oldPriority && goal.getPriority() <= newPriority) {
                goal.setPriority(goal.getPriority() - 1);
            }
            if (direction == -1 && goal.getPriority() < oldPriority && goal.getPriority() >= newPriority) {
                goal.setPriority(goal.getPriority() + 1);
            }
        });
        goalToRearrange.setPriority(newPriority);
        List<GoalInfo> savedGoals = goalService.updateAll(userInfo, goals);
        return ResponseEntity.ok(new FinancialGoalListResponseDTO(savedGoals, savedGoals.size()));
    }

    @GetMapping("/stat-chart")
    public ResponseEntity<Map<String, Object>> getStatChart(@AuthenticationPrincipal FirebaseUserDTO user) {
        UserInfo userInfo = userRepository.findByEmail(user.getEmail());
        return ResponseEntity.ok(goalService.getStatChart(userInfo));
    }

}
