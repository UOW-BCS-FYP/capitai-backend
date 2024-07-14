package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetCategoryCreateDTO {

    private String title;
    private double amount;
    private boolean isBill;
    private int intervalMonth;
    private boolean isActivated;

}
