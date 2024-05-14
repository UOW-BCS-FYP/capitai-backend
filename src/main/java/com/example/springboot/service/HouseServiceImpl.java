// package com.example.springboot.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.springboot.dao.HouseDao;
// import com.example.springboot.model.House;

// @Service
// public class HouseServiceImpl implements HouseService{

//     @Autowired
// 	private HouseDao houseDao;

// 	public HouseDao getHouseDao() {
// 		return houseDao;
// 	}

// 	public void setHouseDao(HouseDao houseDao) {
// 		this.houseDao = houseDao;
// 	}


// 	public int save(House entity) {
// 		// TODO Auto-generated method stub
// 		return houseDao.save(entity);
// 	}

// 	public int put(House entity) {
// 		// TODO Auto-generated method stub
// 		return houseDao.put(entity);
// 	}

// 	public List<House> search(String val) {
// 		// TODO Auto-generated method stub
// 		return houseDao.search(val);
// 	}

// }
