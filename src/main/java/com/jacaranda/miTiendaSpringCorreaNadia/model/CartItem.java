package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.Objects;

public class CartItem {

	private int eleId;
	private int quantity; 
	
	
	public CartItem() {
		super();
	
	}

	public CartItem(int eleId, int quantity) {
		super();
		this.eleId = eleId;
		this.quantity = quantity;
	}


	public int getEleId() {
		return eleId;
	}


	public void setEleId(int eleId) {
		this.eleId = eleId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return eleId == other.eleId;
	}

	@Override
	public String toString() {
		return "CartItem [eleId=" + eleId + ", quantity=" + quantity + "]";
	}
	
}
