package com.example.springboot.dao;

import org.springframework.stereotype.Repository;

import com.example.springboot.model.Dispute;
import com.example.springboot.model.UserInfo;
import com.example.springboot.model.BookingInfo;

@Repository
public interface DisputeRepository extends RefreshableCRUDRepository<Dispute, Long> {
    Dispute findFirstById(Long id);
    Dispute[] findAllByUserInfo(UserInfo userInfo);
    Dispute[] findAllByBookingInfo(BookingInfo bookingInfo);
}