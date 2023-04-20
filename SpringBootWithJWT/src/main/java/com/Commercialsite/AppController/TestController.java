package com.Commercialsite.AppController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class TestController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

}
