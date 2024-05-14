package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.UserRole;

@Repository
public interface RoleRepository extends RefreshableCRUDRepository<UserRole, Long> {
    public UserRole findByName(String role);
    public UserRole findFirstById(Long id);
}
