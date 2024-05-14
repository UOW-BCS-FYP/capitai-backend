package com.example.springboot.dao;

import com.example.springboot.model.TourInfo;
import com.example.springboot.model.TourReview;

public interface TourReviewRepository extends RefreshableCRUDRepository<TourReview, Long> {
    TourReview findFirstById(Long id);
    TourReview findFirstByTourInfo(TourInfo tourInfo);
    TourReview[] findAllByTourInfo(TourInfo tourInfo);
}
