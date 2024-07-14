package com.example.springboot.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeAndSpendingResponseDTO {

    private long id;
    private String title;
    private double amount;
    private Date date;
    private String subject;
    private boolean isIncome;
    private String category;
    
}
