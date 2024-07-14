package com.example.springboot.service;

import com.example.springboot.model.UserInfo;

public interface MockDataService {
    
    public void generateAllData(UserInfo userInfo);
    public void deleteAllData(UserInfo userInfo);
    
}
