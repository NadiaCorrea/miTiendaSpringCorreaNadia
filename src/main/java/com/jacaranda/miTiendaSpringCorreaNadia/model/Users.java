package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Users implements UserDetails {
	@Id
	private String username;
	
	@NotEmpty(message = "La contraseña no puede quedar vacía")
	private String password;
	
	@NotEmpty(message = "El nombre no puede quedar vacío")
	@Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
	private String name;
	
	@NotEmpty(message = "El email no puede quedar vacío")
	@Email(message = "Debe cumplir con el formato de email: ejemplo@email.com")
	private String email;
	
	private String role;
	private String verificationcode;
	private boolean enabled;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Orders> orders = new ArrayList<>();
	
	
	public Users() {
		super();
	}


	public Users(String username, String password, String name, String email) throws UserException {
		super();
		setUsername(username);
		setPassword(password);
		setName(name);
		setEmail(email);
		setRole("USER");
	}
	
	
	public Users(String username, String password, String name, String email, String role) throws UserException {
		super();
		setUsername(username);
		setPassword(password);
		setName(name);
		setEmail(email);
		setRole(role);

	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) throws UserException {
		
		if(username == null || username.length() > 20 || username.isBlank()) {
			throw new UserException("El nombre de usuario no puede estar vacío o tener más de 20 caracteres.");
			
		} else {
			this.username = username;
		}
		
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) throws UserException {
		
		if(password ==null || password.isBlank()) {
			throw new UserException("La contraseña no puede estar vacía ni dejarse en blanco.");
		} else {
			this.password = password;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) throws UserException {
		
		if(name == null || name.isBlank()) {
			throw new UserException("El nombre no puede estar vacío.");
		} else {
			this.name = name;
		}
	
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) throws UserException {
		
		if(email == null || email.isBlank()) {
			throw new UserException("El email no puede estar vacío.");
		} else {
			this.email = email;
		}
	}
		
	public String getRole() {
		return role;
	}

	public void setRole(String role) throws UserException {
		if(!role.equals("USER")  && !role.equals("ADMIN")) {
			throw new UserException("El rol no es válido.");
		} else {
			this.role = role;
		}
		
	}

	
	public String printAdmin() {
		String result = "No";
		
		if (getRole().equals("ADMIN") ) {
			result = "Si";
		}
		
		return result;		
	}
	

	public String getVerificationcode() {
		return verificationcode;
	}


	public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public List<Orders> getOrders() {
		return orders;
	}


	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}


	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		return Objects.equals(username, other.username);
	}



	@Override
	public String toString() {
		return "Users [username=" + username + ", password=" + password + ", name=" + name + ", email=" + email
				+ ", role=" + role + ", verificationcode=" + verificationcode + ", enabled=" + enabled + ", orders="
				+ orders + "]";
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		GrantedAuthority authority = new SimpleGrantedAuthority(this.role);
		authorities.add(authority);
		
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	
	

}