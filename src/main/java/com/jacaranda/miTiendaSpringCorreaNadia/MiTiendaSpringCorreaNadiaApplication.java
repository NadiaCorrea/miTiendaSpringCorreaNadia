package com.jacaranda.miTiendaSpringCorreaNadia;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;

@SpringBootApplication
public class MiTiendaSpringCorreaNadiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiTiendaSpringCorreaNadiaApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() { 
		Cloudinary cloudinary = null; 
		Map<String, String> config = new HashMap<String, String>(); 
		config.put("cloud_name", "dhqubydyc"); config.put("api_key", "997847851497246");
		config.put("api_secret", "Eei-793wwENGe9jjrxL1x9tBMD8"); 
		cloudinary = new Cloudinary(config); 
		
		return cloudinary; 
		} 
}
