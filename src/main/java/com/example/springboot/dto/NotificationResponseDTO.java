package com.example.springboot.dto;

import java.util.List;

import com.example.springboot.model.NotificationInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationResponseDTO {
    
    private List<NotificationInfo> data;
    private int total;

}
