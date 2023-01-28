package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {

	private List<CartItem> requestedItems = new ArrayList<>();

	public ShoppingCart() {
		super();

	}

	public List<CartItem> getRequestedItems() {
		return requestedItems;
	}

	public void setRequestedItems(List<CartItem> requestedItems) {
		this.requestedItems = requestedItems;
	}

	public CartItem getItemByElementId(int eleId) {
		CartItem result = null;

		Iterator<CartItem> iterator = this.requestedItems.iterator();

		while (iterator.hasNext()) {
			CartItem iItem = iterator.next();

			if (iItem.getEleId() == eleId) {
				result = iItem;
			}
		}
		return result;
	}


	public void addItem(CartItem item) throws ShoppingCartException {
		
		CartItem existingItem = getItemByElementId(item.getEleId());
		
		if (existingItem != null) {
			// it removes the old one 
			this.requestedItems.remove(existingItem);
			//it sets the new quantity
			existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
			//it adds the modified item 
			this.requestedItems.add(existingItem);
		} else {
			this.requestedItems.add(item);
		}
	
	}

	@Override
	public String toString() {
		return "ShoppingCart [requestedItems=" + requestedItems + "]";
	}

}
