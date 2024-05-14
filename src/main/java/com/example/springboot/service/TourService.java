package com.example.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.dto.TourSearchRequestDTO;
import com.example.springboot.model.TourInfo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class TourService {
    
    private final EntityManager entityManager;

    @Autowired
    public TourService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TourInfo[] searchTour(TourSearchRequestDTO request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TourInfo> query = cb.createQuery(TourInfo.class);
        Root<TourInfo> root = query.from(TourInfo.class);

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            query.where(cb.like(root.get("title"), "%" + request.getTitle() + "%"));
        }

        if (request.getLocation() != null && !request.getLocation().isEmpty()) {
            query.where(cb.like(root.get("location"), "%" + request.getLocation() + "%"));
        }

        if (request.getDuration() != null && !request.getDuration().isEmpty()) {
            query.where(cb.like(root.get("duration"), "%" + request.getDuration() + "%"));
        }

        if (request.getPriceMin() > 0) {
            query.where(cb.ge(root.get("price"), request.getPriceMin()));
        }
        if (request.getPriceMax() > 0) {
            query.where(cb.le(root.get("price"), request.getPriceMax()));
        }

        List<TourInfo> tours = entityManager.createQuery(query).getResultList();

        return tours.toArray(new TourInfo[tours.size()]);
    }

}
