package com.example.springboot.dto;

import java.util.List;

import com.example.springboot.model.IncomeAndSpendingInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeAndSpendingListResponseDTO {
    List<IncomeAndSpendingResponseDTO> data;
    int total;
}
