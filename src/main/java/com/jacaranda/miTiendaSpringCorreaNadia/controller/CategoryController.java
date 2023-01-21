package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import java.util.List;
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

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;
import com.jacaranda.miTiendaSpringCorreaNadia.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService catService;

	// It shows the list of categories
	@GetMapping("/categoria/list")
	public String showCategories(Model model, @RequestParam("pageNumber") Optional<Integer> pageNumber,
			@RequestParam("sizeNumber") Optional<Integer> sizeNumber,
			@RequestParam("sortField") Optional<String> sortField,
			@RequestParam("stringFind") Optional<String> stringFind) {

		Page<Categories> page = catService.findAll(pageNumber.orElse(1), sizeNumber.orElse(5), sortField.orElse("catId"), stringFind.orElse(""));
		
		model.addAttribute("currentPage", pageNumber.orElse(1));
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField.orElse("catId"));
		model.addAttribute("stringFind", stringFind.orElse(""));
		model.addAttribute("categories", page.getContent());

		return "categoriesList";
	}
	

	// It shows the form to add a category
	@GetMapping("/categoria/add")
	public String addCategory(Model model) {

		Categories newCat = new Categories();

		model.addAttribute("category", newCat);

		return "categoryAddForm";
	}

	//It receives a new category and adds it if it doesn't exist
	@PostMapping("/categoria/add/submit")
	public String addCategorySubmit(@Validated @ModelAttribute("category") Categories newCat, Model eModel,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "categoryAddForm";

		} else {
			try {
				catService.addCategory(newCat);
				return "redirect:/categoria/list";

			} catch (Exception e) {

				eModel.addAttribute("errorMessage", e.getMessage());
				return "error";
			}
		}

	}
	
	//It receives the id of the category to be deleted and returns a form with the info of the category
	@GetMapping("/categoria/delete")
	public String deleteCategory(Model model, @RequestParam(name = "id") int catId) {
		
		Categories existingCat = catService.getCategory(catId);
		
		if (existingCat != null) {
			model.addAttribute("category", existingCat);
			return "deleteCategory";
			
		}else {
			model.addAttribute("errorMessage", "No existe la categoría.");
			return "error";
		}
		
	}
	
	@PostMapping("/categoria/delete/submit")
	public String deleteCategorySubmit(@ModelAttribute("category") Categories category, Model eModel) {
		
		try {
			catService.deleteCategory(category);
			return "redirect:/categoria/list";
			
		} catch (Exception e) {
			
			eModel.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
	}

	// It gets the information of the category requested by the ID
	@GetMapping("/categoria/update")
	public String updateCategory(Model model, @RequestParam(name = "id") int catId) {
		
		Categories existingCat = catService.getCategory(catId);
		
		if (existingCat != null) {
			model.addAttribute("category", existingCat);
			return "updateCategory";
			
		}else {
			model.addAttribute("errorMessage", "La categoría no existe.");
			return "error";
		}
		
	}
	
	
	@PostMapping("/categoria/update/submit")
	public String updateCategorySubmit(@Validated @ModelAttribute("category") Categories category, Model eModel) {
	
		try {
			catService.updateCategory(category);
			return "redirect:/categoria/list";
			
		} catch (Exception e) {
			
			eModel.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}
	
	
	
}
