package com.example.springboot.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "TOURS")
public class TourInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    private String title;
    // location: string
    // price: number
    // duration: string
    // description: string,
    // status: 'active' | 'inactive'

    private String location;
    private double price;
    private String duration;
    private String description;
    private String status;
    private Date createdDate;
    private String tags;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @OneToMany(mappedBy = "tourInfo", fetch = FetchType.LAZY)
    private Set<TourReview> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    private UploadedFileInfo picture;
}
