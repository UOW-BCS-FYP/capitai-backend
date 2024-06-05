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
@Table(name = "SPENDING")
public class SpendingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    private String title;
    private double amount;
    private Date createdDate;
    private String issuedTo;
    
    @ManyToOne
    @JoinColumn(name = "budgetCtgy_id", referencedColumnName = "id")
    private BudgetCtgyInfo budgetCtgyInfo;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") //budget category already has user as foreign key, this column might be redundant
    private UserInfo userInfo;
}
