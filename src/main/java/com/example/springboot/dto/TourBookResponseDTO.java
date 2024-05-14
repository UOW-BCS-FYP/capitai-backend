package com.example.springboot.dto;

import java.util.Date;

import com.example.springboot.model.TourInfo;
import com.example.springboot.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TourBookResponseDTO {

    private long id;
    private TourInfo tourInfo;
    private UserInfo userInfo;
    private Date bookingDate;
    private String remarks;
    private int noOfPersons;
    private double totalAmount;
    private String status;
    private String paymentStatus;
    private String paymentMode; // UPI, Card, NetBanking, Wallet
    private String paymentRefId;
    private String paymentRemarks;
    private Date createdDate;
}
