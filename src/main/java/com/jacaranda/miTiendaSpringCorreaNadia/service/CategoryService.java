package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;
import com.jacaranda.miTiendaSpringCorreaNadia.model.CategoryException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository catRepository;
	
	//It gets a category by its ID
	public Categories getCategory(int catId) {
		return catRepository.findById(catId).orElse(null);
	}
	
	//It gets a category by its name
	public Categories getCategoryByName(String name) {
		return catRepository.findOneByNameIgnoreCase(name);
	}
	
	//It gets all categories
	public List<Categories> getCategories(){
		return catRepository.findAll();
	}
	
	//It adds a category to the DB if it doesn't exist
	public Categories addCategory(Categories category) throws CategoryException {
		
		Categories existingCat = getCategoryByName(category.getName());
		
		if(existingCat == null) {
		
			existingCat = catRepository.save(category);
			
		} else {
			throw new CategoryException("La categoría ya existe en la base de datos.");
		}
		
		return existingCat;
	}
	
	//It removes a category from the DB if it exists
	public void deleteCategory(Categories category) throws CategoryException {
		
		if(getCategory(category.getCatId()) != null) {
			catRepository.delete(category);
		} else {
			throw new CategoryException("La categoría no existe en la base de datos.");
		}
		
	}
	
	//It updates a category on the DB if it exists
	public Categories updateCategory(Categories category) throws CategoryException {
		
		Categories existingCat = getCategory(category.getCatId());
		
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
	
	public Page<Categories> findAll(int pageNum, int pageSize, String sortField, String stringFind) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortField).ascending());

		if(stringFind.equals("")) {
			return catRepository.findAll(pageable);
		}else {
			return catRepository.findByNameLike("%" + stringFind + "%", pageable); 
		}
	
	}
	
}
