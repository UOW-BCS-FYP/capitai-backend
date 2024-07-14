package com.example.springboot.dto;

import java.util.List;

import com.example.springboot.model.ExpIncInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpIncListResponseDTO {
    List<ExpIncInfo> data;
    int total;
}
