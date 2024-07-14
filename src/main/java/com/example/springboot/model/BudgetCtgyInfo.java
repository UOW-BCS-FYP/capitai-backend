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
@Table(name = "BUDGETCATEGORY")
public class BudgetCtgyInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;

    private String title;

    private double amount;
    private boolean isBill;
    private int intervalMonth;
    private Date lastInterval;
    private boolean isActivated;
    // private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    //@OneToMany(mappedBy = "BudgetCtgyInfo", fetch = FetchType.LAZY)
    //private Set<SpendingInfo> reviews = new HashSet<>();
}
