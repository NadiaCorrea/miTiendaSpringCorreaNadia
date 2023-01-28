package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Orders;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

	
	public List<Orders> findAllByUserUsername(String username);
}
