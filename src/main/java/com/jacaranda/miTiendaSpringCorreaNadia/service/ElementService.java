package com.jacaranda.miTiendaSpringCorreaNadia.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Categories;
import com.jacaranda.miTiendaSpringCorreaNadia.model.ElementException;
import com.jacaranda.miTiendaSpringCorreaNadia.model.Elements;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.CategoryRepository;
import com.jacaranda.miTiendaSpringCorreaNadia.repository.ElementRepository;

@Service
public class ElementService {
	@Autowired
	ElementRepository eleRepository;
	@Autowired
	CategoryRepository catRepository;

	// It gets an article by its ID
	public Elements getElement(int eleId) {
		return eleRepository.findById(eleId).orElse(null);
	}

	// It gets an article by its name
	public Elements getElementByName(String name) {
		return eleRepository.findOneByNameIgnoreCase(name);
	}

	// It get all the articles on the DB
	public List<Elements> getElements() {
		return eleRepository.findAll();
	}

	// It adds an article to the DB if it doesn't exist
	public Elements addElement(Elements element) throws ElementException {

		Elements existingEle = getElementByName(element.getName());
		Categories existingCat = catRepository.findById(element.getCategory().getCat_id()).orElse(null);

		if (existingCat != null) {

			if (existingEle == null) {

				element.setCategory(existingCat);

				existingEle = eleRepository.save(element);

			} else {
				throw new ElementException("El artículo ya existe en la base de datos.");
			}
		} else {
			throw new ElementException("La categoría del artículo no existe en la base de datos.");
		}

		return existingEle;
	}

	// It removes an article from the DB if it exists
	public void deleteElement(Elements element) throws ElementException {

		Elements existingEle = getElement(element.getEleId());

		if (existingEle != null) {
			eleRepository.delete(existingEle);

		} else {
			throw new ElementException("El elemento no existe en la base de datos.");
		}
	}

	// It updates an article on the DB if it exists
	public Elements updateElement(Elements element) throws ElementException {

		Elements existingEle = getElement(element.getEleId());
		Categories existingCat = catRepository.findById(element.getCategory().getCat_id()).orElse(null);

		if (existingCat != null) {

			if (existingEle != null) {

				try {
					existingEle.setName(element.getName());
					existingEle.setDescription(element.getDescription());
					existingEle.setPrice(element.getPrice());
					existingEle.setStock(element.getStock());
					existingEle.setCategory(existingCat);

					return eleRepository.save(existingEle);

				} catch (ElementException e) {
					e.printStackTrace();
					throw new ElementException("Ha habido un error al añadir el artículo a la base de datos.");
				}

			} else {
				throw new ElementException("El artículo no existe en a la base de datos.");
			}

		} else {
			throw new ElementException("La categoría del artículo no existe en la base de datos.");
		}
	}
	
	public Page<Elements> findAll(int pageNum, int pageSize, String sortField, String stringFind) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(sortField).ascending());

		if(stringFind.equals("")) {
			return eleRepository.findAll(pageable);
		}else {
			return eleRepository.findByNameLike("%" + stringFind + "%", pageable); 
		}
	
	}
	
}
