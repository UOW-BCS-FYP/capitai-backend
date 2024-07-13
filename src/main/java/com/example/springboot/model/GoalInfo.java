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
@Table(name = "GOAL")
public class GoalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;
    private String title;
    private String type;
    private double amount;
    private int priority;
    private boolean completed;

    // capital building
    private Date deadline;

    // debt payment
    private double interest;
    private int paymentInterval;

    // long term expense
    private int frequency;

    // progress fields - calculated by the system
    private double progress;
    private Date completeDate;
    private double remainingDebt;
    private double interestPaid;
    private int remainingMonths;
    private Date[] completeDates;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;
}
