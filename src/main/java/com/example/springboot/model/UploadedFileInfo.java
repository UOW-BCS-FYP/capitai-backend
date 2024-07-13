package com.example.springboot.model;

import java.sql.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "UploadedFile")
public class UploadedFileInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
    private long id;

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo uploadedBy;

    @CreatedDate
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private FinancialConsultantMessageInfo financialConsultantMessageInfo;

}
