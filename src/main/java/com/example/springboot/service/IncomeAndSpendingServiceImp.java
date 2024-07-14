package com.example.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.springboot.dao.IncomeAndSpendingInfoRepository;
import com.example.springboot.model.IncomeAndSpendingInfo;
import com.example.springboot.model.UserInfo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IncomeAndSpendingServiceImp implements IncomeAndSpendingService {
    @Autowired
    private IncomeAndSpendingInfoRepository inSInfoRepository;

    public List<IncomeAndSpendingInfo> getAllInSInfo(UserInfo userInfo) {
        return StreamSupport.stream(inSInfoRepository.findAllByUserInfo(userInfo).spliterator(), false)
            .collect(Collectors.toList());
    }

    public Page<IncomeAndSpendingInfo> getAllInSInfo(UserInfo userInfo,String searchQuery, Date dateStart, Date dateEnd, Boolean isIncome, Double min, Double max, PageRequest pageRequest) {
        return inSInfoRepository.findByCriteria(userInfo, searchQuery, dateStart, dateEnd, isIncome, min, max, pageRequest);
    }

    public IncomeAndSpendingInfo saveInSInfo(UserInfo userInfo, IncomeAndSpendingInfo inSInfo) {
        inSInfo.setUserInfo(userInfo);
        return inSInfoRepository.save(inSInfo);
    }

    public void deleteInSInfo(UserInfo userInfo, Long id) {
        inSInfoRepository.deleteByIdAndUserInfo(id, userInfo);
    }

    public IncomeAndSpendingInfo updateInSInfo(UserInfo userInfo, Long id, IncomeAndSpendingInfo inSInfo) {
        if (inSInfoRepository.findFirstByIdAndUserInfo(id, userInfo) == null) {
            return null;
        }
        inSInfo.setId(id);
        inSInfo.setUserInfo(userInfo);
        return inSInfoRepository.save(inSInfo);
    }
}