package com.jacaranda.miTiendaSpringCorreaNadia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacaranda.miTiendaSpringCorreaNadia.model.Users;

public interface UserRepository extends JpaRepository<Users, String>{

}
