package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourSearchRequestDTO {

    private String title;
    private String location;
    private double priceMax;
    private double priceMin;
    private String duration;

}
