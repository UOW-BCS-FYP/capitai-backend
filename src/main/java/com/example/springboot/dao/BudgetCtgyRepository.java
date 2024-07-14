package com.example.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot.model.BudgetCtgyInfo;
import com.example.springboot.model.UserInfo;

public interface BudgetCtgyRepository extends RefreshableCRUDRepository<BudgetCtgyInfo, Long> {
	
	BudgetCtgyInfo findFirstById(Long id);
    BudgetCtgyInfo findFirstByIdAndUserInfo(Long id, UserInfo userInfo);
    BudgetCtgyInfo findFirstByTitleAndUserInfo(String title, UserInfo userInfo);
	BudgetCtgyInfo[] findAllByUserInfo(UserInfo userInfo);

	@Query("SELECT b FROM BudgetCtgyInfo b WHERE " +
        "(:userInfo is null OR b.userInfo = :userInfo) AND " +
        "(:query is null OR LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
        "(:isBill is null OR b.isBill = :isBill) AND " +
        "(:isActivated is null OR b.isActivated = :isActivated) AND " +
        "(:min is null OR b.amount >= :min) AND " +
        "(:max is null OR b.amount <= :max)")
    Page<BudgetCtgyInfo> getBudgetCategories(@Param("userInfo") UserInfo userInfo,
                                            @Param("query") String query,
                                            @Param("isBill") Boolean isBill,
                                            @Param("isActivated") Boolean isActivated,
                                            @Param("min") Integer min,
                                            @Param("max") Integer max,
                                            Pageable pageable);

	void deleteById(Long id);
    void deleteByIdAndUserInfo(Long id, UserInfo userInfo);
}
