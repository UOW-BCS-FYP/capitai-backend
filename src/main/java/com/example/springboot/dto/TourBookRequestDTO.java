package com.example.springboot.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourBookRequestDTO {

    private Date bookingDate;
    private String remarks;
    private int noOfPersons;

}
