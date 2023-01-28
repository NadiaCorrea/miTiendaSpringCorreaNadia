package com.jacaranda.miTiendaSpringCorreaNadia.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.miTiendaSpringCorreaNadia.model.CartItem;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrder;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrderException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Orders;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ShoppingCart;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ShoppingCartException;

import jakarta.servlet.http.HttpSession;

@Service
public class ShoppingCartService {
	
	@Autowired
	HttpSession session;
	@Autowired
	ElementService eleService;


	// add item to shopping cart
	public void addItemToCart(CartItem item) throws ShoppingCartException {

		if (item != null && item.getEleId() > 0 && item.getQuantity() > 0) {

			ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");

			if (cart == null) {
				cart = new ShoppingCart();
			}

			Elements ele = eleService.getElement(item.getEleId());

			if (ele != null && ele.getStock() >= item.getQuantity()) {
				cart.addItem(item);
				session.setAttribute("ShoppingCart", cart);
				System.out.println(cart.toString());
			} else {
				throw new ShoppingCartException("La cantidad solicitada no puede ser mayor que el stock.");
			}

		} else {
			throw new ShoppingCartException("El artículo no puede ser nulo y la cantidad debe ser mayor que 0.");
		}
	}

//it returns elementOrder list from the shopping cart 

	public List<ElementOrder> getElementOrdersFormCart(Orders order) throws ShoppingCartException {
		List<ElementOrder> result = new ArrayList<>();

		ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");
		if (cart != null) {
			try {
				
				Iterator<CartItem> iterator = cart.getRequestedItems().iterator();

				while (iterator.hasNext()) {
					CartItem iItem = iterator.next();
					Elements ele = eleService.getElement(iItem.getEleId());

					if (ele != null) {
						
						if (iItem.getQuantity() > ele.getStock()) {
							iItem.setQuantity(ele.getStock());
						}
						
						ElementOrder eleOrd = new ElementOrder(ele, order, iItem.getQuantity());
						result.add(eleOrd);
					} else {
						throw new ShoppingCartException("El artículo no existe o la cantidad supera el stock existente.");
					}
				}

			} catch (ElementOrderException e) {
				e.printStackTrace();
				throw new ShoppingCartException("Ha ocurrido un error al procesar el pedido.");
			}
		}

		return result;

	}

}
