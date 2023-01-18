package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;

public interface ElementRepository extends JpaRepository<Elements, Integer>{

	Elements findOneByNameIgnoreCase(String name);
	
	public Page<Elements> findByNameLike(String keyword, Pageable pageable); 
}
