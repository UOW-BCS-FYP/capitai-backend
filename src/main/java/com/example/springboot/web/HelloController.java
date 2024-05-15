package com.example.springboot.web;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {
	
	@ResponseBody
    @RequestMapping("/hello")
    public String hello() {
		return "this is in demo2 in tutorial";
    }

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "127.0.0.1");
        return "index";
    }

}