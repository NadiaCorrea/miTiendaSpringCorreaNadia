package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;
import com.jacaranda.miTiendaSpringCorreaNadia.model.CategoryException;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository catRepository;
	
	public Categories getCategory(int catId) {
		return catRepository.findById(catId).orElse(null);
	}
	
	public Categories getCategoryByName(String name) {
		return catRepository.findOneByNameIgnoreCase(name);
	}
	
	public List<Categories> getCategories(){
		return catRepository.findAll();
	}
	
	public Categories addCategory(Categories category) throws CategoryException {
		Categories existingCat = getCategoryByName(category.getName());
		
		if(existingCat == null) {
			existingCat = catRepository.save(category);
			
		} else {
			throw new CategoryException("La categoría ya existe en la base de datos.");
		}
		
		return existingCat;
	}
	
	public void deleteCategory(Categories category) throws CategoryException {
		
		if(getCategory(category.getCat_id()) != null) {
			catRepository.delete(category);
		} else {
			throw new CategoryException("La categoría no existe en la base de datos.");
		}
		
	}
	
	public Categories updateCategory(Categories category) throws CategoryException {
		
		Categories existingCat = getCategory(category.getCat_id());
		
		if(existingCat != null) {
			try {
				existingCat.setName(category.getName());
				existingCat.setDescription(category.getDescription());
				
				return catRepository.save(existingCat);
				
			} catch (CategoryException e) {
				e.printStackTrace();
				throw new CategoryException("Ha habido un error al añadir la categoría a la base de datos.");
			}
			
		} else {
			throw new CategoryException("La categoría no existe en la base de datos.");
		}
		
	}
	
	
	
}
