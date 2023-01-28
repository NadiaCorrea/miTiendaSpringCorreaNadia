package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(ElementOrderId.class)
public class ElementOrder {
	@Id
	@ManyToOne
	@JoinColumn (name = "eleId")
	private Elements element;
	
	@Id
	@ManyToOne
	@JoinColumn (name = "order_id")
	private Orders order;
	private int quantity;
	

	public ElementOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ElementOrder(Elements element, Orders order, int quantity) throws ElementOrderException {
		super();
		setElement(element);
		setOrder(order);
		setQuantity(quantity);
	}

	public Elements getElement() {
		return element;
	}
	
	
	public void setElement(Elements element) throws ElementOrderException {
		
		if (element != null) {
			this.element = element;
		} else {
			throw new ElementOrderException("El producto no puede ser nulo.");
		}
		
	}
	

	public Orders getOrder() {
		return order;
	}
	
	public void setOrder(Orders order) throws ElementOrderException {
		if(order != null) {
			this.order = order;
		} else {
			throw new ElementOrderException("El pedido no puede ser nulo.");
		}
		
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) throws ElementOrderException {
		
		if(quantity > 0) {
			this.quantity = quantity;
			
		}else {
			throw new ElementOrderException("La cantidad no puede ser menor que 0.");
		}
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(element, order);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementOrder other = (ElementOrder) obj;
		return Objects.equals(element, other.element) && Objects.equals(order, other.order);
	}

	@Override
	public String toString() {
		return "ElementOrder [element=" + element + ", order=" + order + ", quantity=" + quantity + "]";
	}
	
	
	
	

}
