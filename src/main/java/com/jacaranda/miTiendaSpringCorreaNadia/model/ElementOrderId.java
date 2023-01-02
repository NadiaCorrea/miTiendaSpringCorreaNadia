package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.io.Serializable;
import java.util.Objects;

public class ElementOrderId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6330117550323088104L;
	private int element;
	private int order;
	
	
	public ElementOrderId() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ElementOrderId(int element, int order) {
		super();
		this.element = element;
		this.order = order;
	}

	public int getElement() {
		return element;
	}



	public void setElement(int element) throws ElementOrderIdException {
		
		if(element > 0) {
			this.element = element;
		} else {
			throw new ElementOrderIdException("El código del producto no puede ser igual o menor que 0.");
		}

	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) throws ElementOrderIdException {
		
		if(order > 0) {
			this.order = order;
		} else {
			throw new ElementOrderIdException("El código del pedido no puede ser igual o menor que 0.");
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
		ElementOrderId other = (ElementOrderId) obj;
		return element == other.element && order == other.order;
	}


	@Override
	public String toString() {
		return "ElementOrderId [element=" + element + ", order=" + order + "]";
	}



}
