package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Categories {
	
	@Id
	private int cat_id;
	private String name; 
	private String description;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Elements> elements;
	
	
	public Categories() {
		super();
		
	}


	public Categories(String name, String description) throws CategoryException {
		super();
		setName(name);
		setDescription(description);
	}


	public int getCat_id() {
		return cat_id;
	}


	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) throws CategoryException {
		
		if(name == null || name.length() > 50 || name.isBlank()) {
			throw new CategoryException("El nombre de la categoría no puede estar vacía o tener más de 50 caracteres.");
			
		} else {
			this.name = name;
		}
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) throws CategoryException {
		
		if(description == null || description.length() > 200 || description.isBlank()) {
			throw new CategoryException("La descripción no puede estar vacía o tener más de 200 caracteres.");
			
		} else {
			this.description = description;
		}
	}


	public List<Elements> getElements() {
		return elements;
	}


	public void setElements(List<Elements> elements) {
		this.elements = elements;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cat_id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categories other = (Categories) obj;
		return cat_id == other.cat_id;
	}


	@Override
	public String toString() {
		return "Categories [cat_id=" + cat_id + ", name=" + name + ", description=" + description + ", elements="
				+ elements + "]";
	}
	
	
	

}
