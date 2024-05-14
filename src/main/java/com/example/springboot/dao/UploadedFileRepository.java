package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.UploadedFileInfo;

@Repository
public interface UploadedFileRepository extends RefreshableCRUDRepository<UploadedFileInfo, Long> {
    
}
