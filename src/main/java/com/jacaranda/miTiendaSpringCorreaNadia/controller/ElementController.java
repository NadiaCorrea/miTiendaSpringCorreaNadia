package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.service.CategoryService;
import com.jacaranda.miTiendaSpringCorreaNadia.service.ElementService;

@Controller
public class ElementController {
	
	@Autowired
	private ElementService eleService;
	@Autowired
	private CategoryService catService;
	
	
	// It shows the list of articles
	@GetMapping("/articulo/list")
	public String showArticles(Model model) {
		
		model.addAttribute("elements", eleService.getElements());
		
		return "elementsList";
	}
	
	// It shows the form to add an article
	@GetMapping("/articulo/add")
	public String addArticle(Model model) {
		
		Elements newEle = new Elements();
		List<Categories> categories =  catService.getCategories();
		
		model.addAttribute("element", newEle);
		model.addAttribute("categories", categories);
		
		return "elementAddForm";
		
	}
	 
	@PostMapping("/articulo/add/submit")
	public String addArticleSubmit(@Validated @ModelAttribute("element") Elements ele, Model eModel, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "elementAddForm";
		} else {
			
			try {
				eleService.addElement(ele);
				return "redirect:/articulo/list";
			} catch (Exception e) {
				eModel.addAttribute("errorMessage", e.getMessage());
				return "error";
			}
			
		}
	}
	
	@GetMapping("/articulo/delete")
	public String deleteArticle(Model model, @RequestParam(name = "id") int eleId) {
		
		Elements existingEle = eleService.getElement(eleId);
		
		if(existingEle != null) {
			model.addAttribute("element", existingEle);
			return "deleteElement";
			
		} else {
			model.addAttribute("errorMessage", "No existe el artículo.");
			return "error";
		}
		
	}
	
	@PostMapping("/articulo/delete/submit")
	public String deleteArticleSubmit(@ModelAttribute("element") Elements ele, Model eModel) {
		
		try {
			
			eleService.deleteElement(ele);
			return "redirect:/articulo/list";
			
		} catch (Exception e) {
			eModel.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
	}
	
	@GetMapping("/articulo/update")
	public String updateArticle(Model model, @RequestParam(name = "id") int eleId) {
		
		Elements existingEle = eleService.getElement(eleId);
		List<Categories> categories =  catService.getCategories();
		
		if(existingEle != null) {
			model.addAttribute("element", existingEle);
			model.addAttribute("categories", categories);
			return "updateElement";
			
		} else {
			model.addAttribute("errorMessage", "No existe el artículo.");
			return "error";
		}
	}
	
	@PostMapping("/articulo/update/submit")
	public String updateArticleSubmit(@ModelAttribute("element") Elements element, Model eModel) {
		
		try {
			eleService.updateElement(element);
			return "redirect:/articulo/list";
			
		} catch (Exception e) {
			eModel.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		
		
	}
}
