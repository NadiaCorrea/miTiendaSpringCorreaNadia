package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrder;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.model.OrderException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Orders;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.ElementOrderRepository;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.ElementRepository;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository ordRepository;
	
	@Autowired
	UserService userServ;
	
	@Autowired
	ElementRepository eleRepo;
	
	public Orders getOrder(int orderId) {
		return ordRepository.findById(orderId).orElse(null);
	}
	
	public List<Orders> getOrders(){
		return ordRepository.findAll();
	}
	
	public List<Orders> getAllOrdersByUser(String username) {
		
		return ordRepository.findAllByUserUsername(username);
		
	}
		
	public Orders saveOrder(Orders ord) throws OrderException {
		Orders orderDB =  ordRepository.save(ord);
		
		List<ElementOrder> elements =  orderDB.getElementOrder();
		
		try {
			for(ElementOrder ele: elements) {
				
				Elements iEle =  ele.getElement();
				
				iEle.setStock(iEle.getStock() - ele.getQuantity());
				eleRepo.save(iEle);
			
			}
		
			return orderDB; 
		}catch (Exception e) {
			throw new OrderException("No se ha podido actualizar el stock de los artículos.");
		}
		
		
	}
	
}
