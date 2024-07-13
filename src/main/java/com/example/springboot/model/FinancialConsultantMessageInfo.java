package com.example.springboot.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "FINANCIAL_CONSULTANT_MESSAGE")
public class FinancialConsultantMessageInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;

    private String msg;
    private String senderId;
    private String type;

    @OneToMany(mappedBy = "financialConsultantMessageInfo", fetch = FetchType.LAZY)
    private Set<UploadedFileInfo> attachments = new HashSet<>();

}
