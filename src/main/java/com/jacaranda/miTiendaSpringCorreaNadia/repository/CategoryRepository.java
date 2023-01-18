package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Integer>{

	Categories findOneByNameIgnoreCase(String name);
	
	public Page<Categories> findByNameLike(String keyword, Pageable pageable); 
}
