package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.BookingInfo;
import com.example.springboot.model.UserInfo;

@Repository
public interface BookingRepository extends RefreshableCRUDRepository<BookingInfo, Long>{

    public BookingInfo findFirstById(Long id);
    public BookingInfo[] findAllByUserInfo(UserInfo userInfo);
    public BookingInfo findByUserInfoAndId(UserInfo userInfo, Long id);
    
}
