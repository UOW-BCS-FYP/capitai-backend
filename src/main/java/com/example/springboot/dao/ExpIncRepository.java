package com.example.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.model.ExpIncInfo;
import com.example.springboot.model.UserInfo;

public interface ExpIncRepository extends RefreshableCRUDRepository<ExpIncInfo, Long> {
	ExpIncInfo findFirstById(Long id);
    ExpIncInfo findFirstByIdAndUserInfo(Long id, UserInfo userInfo);
	ExpIncInfo[] findAllByUserInfo(UserInfo userInfo);
	@Query("SELECT e FROM ExpIncInfo e WHERE " +
        "(:userInfo is null or e.userInfo = :userInfo) and " +
        "(:query is null or e.title like %:query%) and " +
        "(:isRegular is null or e.isRegular = :isRegular) and " +
        "(:isActivated is null or e.isActivated = :isActivated) and " +
        "(:min is null or e.amount >= :min) and " +
        "(:max is null or e.amount <= :max)")
    Page<ExpIncInfo> getExpectedIncome(
        @Param("userInfo") UserInfo userInfo,
        @Param("query") String query,
        @Param("isRegular") Boolean isRegular,
        @Param("isActivated") Boolean isActivated,
        @Param("min") Double min,
        @Param("max") Double max,
        Pageable pageable);
    void deleteById(Long id);
    void deleteAllByUserInfo(UserInfo userInfo);
    void deleteByIdAndUserInfo(Long id, UserInfo userInfo);
}
