package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;

public interface UserRepository extends JpaRepository<Users, String>{
	
	@Query("Select p from Users p where p.username = ?1" )
	Users findByUser(String nombre);

	Users findByVerificationcode(String code);
}
