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
@Table(name = "INS")
public class IncomeAndSpendingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;
    private String title;
    private double amount;
    private Date date;
    private String subject;
    private boolean isIncome;
    // private String category;
    
    @ManyToOne
    @JoinColumn(name = "expInc_id", referencedColumnName = "id")
    private ExpIncInfo expIncInfo;

    @ManyToOne
    @JoinColumn(name = "budgetCategory_id", referencedColumnName = "id")
    private BudgetCtgyInfo category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") //budget category already has user as foreign key, this column might be redundant
    private UserInfo userInfo;
}
