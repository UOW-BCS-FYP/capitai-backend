package com.example.springboot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "FINANCIAL_STATEMENT")
public class FinancialStatementInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;
    private double openingBalance;
    private double totalCredit;
    private double totalDebit;
    private double closingBalance;
    private String month;
    private int year;
    private String userId;
    private String createdAt;
    private String updatedAt;
}
