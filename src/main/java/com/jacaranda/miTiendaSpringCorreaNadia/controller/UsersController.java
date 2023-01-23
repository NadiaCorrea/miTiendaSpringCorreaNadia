package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jacaranda.miTiendaSpringCorreaNadia.model.UserException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.UserPassword;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsersController {

	@Autowired
	private UserService usersService;

	// It show the sign up page
	@GetMapping("/register")
	public String signUp(Model model) {
		Users user = new Users();

		model.addAttribute("newuser", user);

		return "register";
	}

	@PostMapping("register/submit")
	public String signUpSubmit(@Validated @ModelAttribute("newuser") Users newUser,@RequestParam(name="file") MultipartFile file, Model model,
			BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "register";
		} else {
			try {
				
				if (newUser.getPassword() == null || "".equals(newUser.getPassword().trim())) {
					throw new UserException("La contraseña no puede estar vacía.");
				} 
				
				//first it sends the verification mail 				
				String siteURL = request.getRequestURL().toString();
				siteURL = siteURL.replace(request.getServletPath(), "");
				//then it sets the photo
				String url = usersService.uploadFile(file);
				newUser.setImage(url);
				//It adds user to database
				usersService.addUser(newUser, siteURL);
				return "addedUser";
				
			} catch (Exception e) {

				model.addAttribute("errorMessage", e.getMessage());

				return "error";
			}
		}

	}
	
	@GetMapping("/verify")
	public String verificateAccount(@RequestParam("code") String code, Model eModel) {
		
		try {
			usersService.verifyUser(code);
			return "verifiedAccount";
		} catch (Exception e) {
			eModel.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}
	

	// shows the list of users
	@GetMapping("/usuario/list")
	public String showUsers(Model model, @RequestParam("pageNumber") Optional<Integer> pageNumber,
			@RequestParam("sizeNumber") Optional<Integer> sizeNumber,
			@RequestParam("sortField") Optional<String> sortField,
			@RequestParam("stringFind") Optional<String> stringFind) {

		Page<Users> page = usersService.findAllUsers(pageNumber.orElse(1), sizeNumber.orElse(10), sortField.orElse("username"), stringFind.orElse(""));
		
		model.addAttribute("currentPage", pageNumber.orElse(1));
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField.orElse("username"));
		model.addAttribute("stringFind", stringFind.orElse(""));
		model.addAttribute("users", page.getContent());

		return "usersList";
	}

	// shows the form for adding a user
	@GetMapping("/usuario/add")
	public String addUser(Model model) {

		Users newUser = new Users();

		model.addAttribute("newUser", newUser);// adding the newUser to the model

		return "userAddForm";
	}

	// adds a user to DB if user doesn't exist
	@PostMapping("/usuario/add/submit")
	public String addSubmitUser(@Validated @ModelAttribute("newUser") Users newUser, Model model,
			BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "userAddForm";
		} else {
			try {
				String siteURL = request.getRequestURL().toString();
				siteURL = siteURL.replace(request.getServletPath(), "");
				usersService.addUser(newUser, siteURL);
				return "addedUser";
			} catch (Exception e) {

				model.addAttribute("errorMessage", e.getMessage());

				return "error";
			}
		}

	}

	// Delete a user
	@GetMapping("/usuario/delete")
	public String deleteUser(Model model, @RequestParam(name = "id") String username) {

		Users user = usersService.getUser(username);

		if (user != null) {
			model.addAttribute("user", user);
			return "deleteUser";

		} else {
			model.addAttribute("errorMessage", "No exite el usuario.");

			return "error";
		}

	}

	@PostMapping("/usuario/delete/submit")
	public String deleteUserSubmit(@ModelAttribute("user") Users user, Model model) {

		try {
			usersService.deleteUser(user);
			return "redirect:/usuario/list";

		} catch (Exception e) {

			model.addAttribute("errorMessage", e.getMessage());

			return "error";
		}

	}

	@GetMapping("/usuario/update")
	public String updateUser(Model model, @RequestParam(name = "id") String username) {

		Users user = usersService.getUser(username);

		if (user != null) {
			model.addAttribute("user", user);
			return "updateUser";

		} else {
			model.addAttribute("errorMessage", "No exite el usuario.");

			return "error";
		}
	}

	@PostMapping("/usuario/update/submit")
	public String updateUserSubmit(@Validated @ModelAttribute("user") Users user, @RequestParam(name="file") MultipartFile file, Model eModel) {

		try {
			
			if(file != null) {
				String url = usersService.uploadFile(file);
				user.setImage(url);
			}
					
			usersService.updateUser(user);
			return "redirect:/articulo/list";

		} catch (Exception e) {
			eModel.addAttribute("errorMessage", e.getMessage());

			return "error";
		}

	}
	
	
	@GetMapping("/usuario/password")
	public String updatePassword(Model model, @RequestParam(name="id") String username) {
		
		UserPassword userPassword = new UserPassword();
		userPassword.setUsername(username);
		
		model.addAttribute("userPassword", userPassword);
		
		
		return "updatePassword";
	}
	
	@PostMapping("/usuario/password/submit")
	public String updatePasswordSubmit(@Validated @ModelAttribute("userPassword") UserPassword userPassword, Model eModel) {
		
		try {
			usersService.updatePassword(userPassword);
			return "redirect:/articulo/list";
			
		} catch (Exception e) {
			eModel.addAttribute("errorMessage", e.getMessage());

			return "error";
		}
		
	}
	

	@GetMapping("/usuario/admin")
	public String updateAdmin(Model model, @RequestParam(name = "id") String username) {

		Users user = usersService.getUser(username);

		if (user != null) {
			model.addAttribute("user", user);
			return "updateAdmin";

		} else {
			model.addAttribute("errorMessage", "No exite el usuario.");
			return "error";
		}
	}

	@PostMapping("/usuario/admin/submit")
	public String updateAdminSubmit(@ModelAttribute("user") Users user, Model model) {

		try {
			usersService.updateAdmin(user);
			return "redirect:/usuario/list";

		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());

			return "error";
		}
	}

}
