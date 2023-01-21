package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


	@GetMapping("/")
	public String welcome() {
			return "index";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
}
