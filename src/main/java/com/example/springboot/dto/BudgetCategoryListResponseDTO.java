package com.example.springboot.dto;

import java.util.List;

import com.example.springboot.model.BudgetCtgyInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetCategoryListResponseDTO {
    List<BudgetCtgyInfo> data;
    int total;
}
