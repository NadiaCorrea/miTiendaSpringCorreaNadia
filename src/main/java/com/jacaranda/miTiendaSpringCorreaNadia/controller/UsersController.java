package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.jacaranda.miTiendaSpringCorreaNadia.model.UserException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.service.UserService;

@Controller
public class UsersController {

	@Autowired
	private UserService usersService;
	
	
	@GetMapping("/")
	public String welcome() {
		
		return "index";
		
	}

	@GetMapping("/usuario/add")
	public String addUser(Model model) {

		Users newUser = new Users();

		model.addAttribute("newUser", newUser);// adding the newUser to the model

		return "userAddForm";
	}

	@PostMapping("/usuario/add/submit")
	public String addSubmitUser(@Validated @ModelAttribute("newUser") Users newUser, Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "userAddForm";
		} else {
				try {
					usersService.addUser(newUser);
					return "addedUser";
				} catch (UserException e) {
					
					model.addAttribute("errorMessage", e.getMessage());
	
					return "error";
				}	
		}

	}
	
	
	
	
	
	
	
	
	

}
