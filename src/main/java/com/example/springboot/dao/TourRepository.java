package com.example.springboot.dao;

import com.example.springboot.model.TourInfo;
import com.example.springboot.model.UserInfo;

public interface TourRepository extends RefreshableCRUDRepository<TourInfo, Long> {
    TourInfo findFirstById(Long id);
    TourInfo[] findAllByUserInfo(UserInfo userInfo);
}
