package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrder;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrderId;

public interface ElementOrderRepository extends JpaRepository<ElementOrder, ElementOrderId>{
	
}
