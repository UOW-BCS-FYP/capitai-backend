package com.example.springboot.dto;

import java.util.List;
import com.example.springboot.model.GoalInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialGoalListResponseDTO {
    List<GoalInfo> data;
    int total;
}
