package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jacaranda.miTiendaSpringCorreaNadia.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService catService;
	
	@GetMapping("/categoria/list")
	public String showCategories(Model model) {
		
		model.addAttribute("categories", catService.getCategories());
		
		return "categoriesList";
	}
	

}
