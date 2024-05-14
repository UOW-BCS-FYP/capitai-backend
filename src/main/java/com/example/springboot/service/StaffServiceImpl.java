// package com.example.springboot.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.springboot.dao.StaffDao;
// import com.example.springboot.model.Staff;

// @Service
// public class StaffServiceImpl implements StaffService{
// 	@Autowired
// 	private StaffDao staffDao;

// 	public StaffDao getStaffDao() {
// 		return staffDao;
// 	}

// 	public void setStaffDao(StaffDao staffDao) {
// 		this.staffDao = staffDao;
// 	}


// 	public int save(Staff entity) {
// 		// TODO Auto-generated method stub
// 		return staffDao.save(entity);
// 	}

// 	/* added by Frank insert example */
	


// 	public List<Staff> list() {
// 		// TODO Auto-generated method stub
// 		System.out.println("in list");

		
// 		return staffDao.list();
// 	}

	
	
// }
