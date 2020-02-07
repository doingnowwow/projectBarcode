package com.hanseon;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TotalController {

	@RequestMapping("/home2")
	public void home() {}
	
	
	public String getBarcode() {
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		
		map.put("name", "");
		
		return "";
	}
	
	
}
