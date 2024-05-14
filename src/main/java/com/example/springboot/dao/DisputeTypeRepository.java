package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.DisputeType;


@Repository
public interface DisputeTypeRepository extends RefreshableCRUDRepository<DisputeType, Long> {
    public DisputeType findByName(String type);
    public DisputeType findFirstById(Long id);
}
