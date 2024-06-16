package com.example.springboot.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKINGS")
public class BookingInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourInfo tourInfo;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
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
