package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Categories {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int catId;
	@NotEmpty(message = "El nombre no puede quedar vacío")
	@Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
	private String name; 
	@NotEmpty(message = "La descripción no puede quedar vacía")
	@Size(max = 200, message = "La descripción no puede tener más de 200 caracteres")
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


	public int getCatId() {
		return catId;
	}


	public void setCatId(int catId) {
		this.catId = catId;
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
		return Objects.hash(catId);
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
		return catId == other.catId;
	}


	@Override
	public String toString() {
		return "Categories [catId=" + catId + ", name=" + name + ", description=" + description + ", elements="
				+ elements + "]";
	}
	
	
	

}
