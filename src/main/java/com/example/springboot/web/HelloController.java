package com.example.springboot.web;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// import com.example.springboot.model.House;
// import com.example.springboot.service.HouseService;
import com.example.springboot.util.Myprint;
import com.example.springboot.util2.Myprint2;

@RestController
@RequestMapping("/")
public class HelloController {
	
	// private static final String ResponseEntity = null;
	// @Autowired
	// private HouseService houseService;
    
    // public HouseService getHouseService() {
	// 	return houseService;
	// }

	// public void setHouseService(HouseService houseService) {
	// 	this.houseService = houseService;
	// }

	@Autowired
	private Myprint2 myprint2;
	
	@ResponseBody
    @RequestMapping("/hello")
    public String hello() {
		
		Myprint myprint1 = new Myprint();
		myprint1.myprint();
		
	//	Myprint myprint2 = new Myprint();
	//	myprint1.myprint();
		
		//dependency injection
		myprint2.myprint();
		
		return "this is in demo2 in tutorial";
     //   return " {key1:a}, {key2:b}, {key3:c}";
    }


	
    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "127.0.0.1");
        return "index";
    }
    
    /* this save is same as update Frank */
    
    // @RequestMapping("/save")
    // public String save(House obj, ModelMap map) {
    	
	// 	int result = 0;
	// 	try{
	// 		result = houseService.save(obj);
	// 		if(result == 0) {
	// 			map.addAttribute("message", "update failed Frank stupid！");
	// 		}else{
	// 			map.addAttribute("message", "update succeed！");
	// 		}
	// 	}catch(Exception e){
	// 		e.printStackTrace();
	// 		map.addAttribute("message", "exception："+e.getMessage());
			
	// 	}
		
		
	// 	return "index";
    // }
    
    // @RequestMapping("/search")
	// public String search(String val, ModelMap map) throws Exception {
		
	// 	List<House> listData = houseService.search(val==null?"":val);
	// 	map.addAttribute("data", listData);
		
	// 	return "index";
	// }
    
    // /* demo insert  Frank */
    
    // @RequestMapping("/put")
    // public String add(House obj, ModelMap map) {
    	

    	
	// 	int result = 0;
	// 	try{
	// 		result = houseService.put(obj);
	// 		if(result == 0) {
	// 			map.addAttribute("message", "inert failed！");
	// 		}else{
	// 			map.addAttribute("message", "insert successful！");
	// 		}
	// 	}catch(Exception e){
	// 		e.printStackTrace();
	// 		map.addAttribute("message", "exception："+e.getMessage());
			
	// 	}
	// 	return "add";
    // }
   
    
    // @RequestMapping("/add")
    // public String add(ModelMap map) {
    //     map.addAttribute("host", "127.0.0.1");
    //     return "add";
    // }
    
}