// package com.example.springboot.web;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;

// import com.example.springboot.model.Staff;

// import com.example.springboot.service.StaffService;

// @Controller
// public class StaffController {
// 	@Autowired
// 	private StaffService staffService;
	
//     public StaffService getStaffService() {
// 		return staffService;
// 	}

// 	public void setUserService(StaffService staffService) {
// 		this.staffService = staffService;
// 	}
	
// 	@ResponseBody
//     @RequestMapping("/staff")
//     public String staffhello() {
//         return "staff Frank 2 hello";
//     }
	
	
//     @RequestMapping("/stafflist")
// 	public String stafflist(ModelMap map) throws Exception {
// 		System.out.println("in listing  3");
		
// 		List<Staff> listData = staffService.list();
// 		map.addAttribute("staffdata", listData);
		
// 		System.out.println("in listing  4");
		
//         return "indexstaff";
//     }
	
	
// }
