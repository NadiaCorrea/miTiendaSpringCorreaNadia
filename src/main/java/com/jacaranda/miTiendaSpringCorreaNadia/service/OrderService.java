package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Orders;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository ordRepository;
	
	public Orders getOrder(int orderId) {
		return ordRepository.findById(orderId).orElse(null);
	}
	
	public List<Orders> getOrders(){
		return ordRepository.findAll();
	}
	
	public List<Orders> getAllOrdersByUser(String username) {
		
		return ordRepository.findAllByUserUsername(username);
		
	}
	
	//add order
	
}
