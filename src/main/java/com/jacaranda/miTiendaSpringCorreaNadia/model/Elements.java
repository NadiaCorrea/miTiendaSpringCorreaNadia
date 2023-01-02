package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Elements {
	@Id
	private int ele_id;
	@NotEmpty(message = "El nombre no puede quedar vacío")
	private String name;
	@NotEmpty(message = "La descripción no puede quedar vacía")
	private String description;
	private double price;
	private int stock;
	
	@ManyToOne
	@JoinColumn(name="category")
	private Categories category;
	
	@OneToMany(mappedBy = "element", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ElementOrder> elementOrder = new ArrayList<>();
	
	
	public Elements() {
		super();
	}
	
	public Elements(String name, String description, double price, int stock, Categories category) throws ElementException {
		super();
		setName(name);
		setDescription(description);
		setPrice(price);
		setStock(stock);
		setCategory(category);
	}

	public int getEle_id() {
		return ele_id;
	}

	public void setEle_id(int ele_id) {
		this.ele_id = ele_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ElementException {
		
		if(name == null || name.length() > 100 || name.isBlank()) {
			throw new ElementException("El nombre del producto no puede estar vacío o tener más de 100 caracteres.");
		} else {
			this.name = name;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws ElementException {
		
		if(description == null || description.length() > 200 || description.isBlank()) {
			throw new ElementException("La descripción del producto no puede estar vacía o tener más de 200 caracteres.");
		} else {
			this.description = description;
		}
		
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) throws ElementException {
		if (price > 0 ) {
			this.price = price;
		} else {
			throw new ElementException("El precio no puede ser menor que 0.");
		}
		
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) throws ElementException {
		
		if(stock < 0) {
			throw new ElementException("El stock no puede ser menor que 0.");
		} else {
			this.stock = stock;
		}
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) throws ElementException {
		if(category != null) {
			this.category = category;
		} else {
			throw new ElementException("La categoría no puede ser nula.");
		}
	}

	public List<ElementOrder> getElementOrder() {
		return elementOrder;
	}

	public void setElementOrder(List<ElementOrder> elementOrder) {
		this.elementOrder = elementOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ele_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elements other = (Elements) obj;
		return ele_id == other.ele_id;
	}

	@Override
	public String toString() {
		return "Elements [ele_id=" + ele_id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", stock=" + stock + ", category=" + category + ", elementOrder=" + elementOrder + "]";
	}
	

	
	
	
	
}
