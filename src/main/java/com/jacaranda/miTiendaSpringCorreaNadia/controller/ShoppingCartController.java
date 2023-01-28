package com.jacaranda.miTiendaSpringCorreaNadia.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jacaranda.miTiendaSpringCorreaNadia.model.CartItem;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementOrder;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Orders;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;
import com.jacaranda.miTiendaSpringCorreaNadia.service.ElementService;
import com.jacaranda.miTiendaSpringCorreaNadia.service.ShoppingCartService;
import com.jacaranda.miTiendaSpringCorreaNadia.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {
	private static final double IVA = 0.21;
	@Autowired
	HttpSession session;
	@Autowired
	ShoppingCartService shopCart;
	@Autowired
	ElementService eleServ;
	@Autowired
	UserService userSer;

	@PostMapping("/shopping/addItem")
	public String addCartItem(@RequestParam(name = "eleId") int eleId, @RequestParam(name = "quantity") int quantity,
			Model eModel) {

		try {
			CartItem newItem = new CartItem(eleId, quantity);
			shopCart.addItemToCart(newItem);
			return "redirect:/articulo/list";
		} catch (Exception e) {
			eModel.addAttribute("errorMessage", "No existe el artículo.");
			return "error";
		}
	}

	@GetMapping("/shopping/showcart")
	public String showCart(Model model) {

		try {
			Users user = userSer.loggedUser();
			Orders ord = new Orders(LocalDateTime.now(), IVA, user);
			List<ElementOrder> elementOrds = shopCart.getElementOrdersFormCart(ord);
			double subtotal = 0;

			for (ElementOrder elementOrd : elementOrds) {
				subtotal = subtotal + (elementOrd.getElement().getPrice() * elementOrd.getQuantity());
			}
			double ivaApplied = subtotal * IVA;
			double total = subtotal + ivaApplied;

			model.addAttribute("elementOrds", elementOrds);
			model.addAttribute("subtotal", subtotal);
			model.addAttribute("IVA", IVA * 100);
			model.addAttribute("ivaApplied", ivaApplied);
			model.addAttribute("total", total);

		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}

		return "cartList";
	}

}
