package com.jacaranda.miTiendaSpringCorreaNadia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jacaranda.miTiendaSpringCorreaNadia.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserService userService;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	// Método que usaremos más abajo
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	// Método que nos suministrará la codificación
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Método que autentifica
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	// Aquí es donde podemos especificar qué es lo que hace y lo que no
	// según el rol del usuario
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> {
			requests.requestMatchers("/").permitAll()
			.requestMatchers("/register/**").permitAll()
			.requestMatchers("/verify").permitAll()
			.requestMatchers("/usuario/update/**").hasAuthority("USER")
			.requestMatchers("/usuario/password/**").hasAuthority("USER")
			.requestMatchers("/articulo/list").hasAnyAuthority("USER", "ADMIN")
			.requestMatchers("/articulo/categoria").hasAnyAuthority("USER", "ADMIN")
			.requestMatchers("/categoria/list").hasAnyAuthority("USER", "ADMIN")
			.requestMatchers("/shopping/**").hasAuthority("USER")
			.requestMatchers("/articulo/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/categoria/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/usuario/**").hasAuthority("ADMIN")
			
			.anyRequest().permitAll();
		})
		.formLogin((form) -> form
				.loginPage("/login")
				.permitAll())
		.logout((logout) -> logout.permitAll());
		
		return http.build();
	}

}
