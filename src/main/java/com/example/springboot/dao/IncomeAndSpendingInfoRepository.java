package com.example.springboot.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.model.IncomeAndSpendingInfo;
import com.example.springboot.model.UserInfo;

public interface IncomeAndSpendingInfoRepository extends RefreshableCRUDRepository<IncomeAndSpendingInfo, Long> {
    List<IncomeAndSpendingInfo> findAllByUserInfo(UserInfo userInfo);
    @Query("SELECT i FROM IncomeAndSpendingInfo i WHERE "
        + "(:userInfo IS NULL OR i.userInfo = :userInfo) AND "
        + "(:searchQuery IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :searchQuery, '%'))) AND "
        + "(:dateStart IS NULL OR i.date >= :dateStart) AND "
        + "(:dateEnd IS NULL OR i.date <= :dateEnd) AND "
        + "(:isIncome IS NULL OR i.isIncome = :isIncome) AND "
        + "(:min IS NULL OR i.amount >= :min) AND "
        + "(:max IS NULL OR i.amount <= :max)")
    Page<IncomeAndSpendingInfo> findByCriteria(@Param("userInfo") UserInfo userInfo,
                                            @Param("searchQuery") String searchQuery, 
                                            @Param("dateStart") Date dateStart, 
                                            @Param("dateEnd") Date dateEnd, 
                                            @Param("isIncome") Boolean isIncome, 
                                            @Param("min") Double min, 
                                            @Param("max") Double max, 
                                            Pageable pageable);

    void deleteByIdAndUserInfo(Long id, UserInfo userInfo);
    IncomeAndSpendingInfo findFirstByIdAndUserInfo(Long id, UserInfo userInfo);
}
