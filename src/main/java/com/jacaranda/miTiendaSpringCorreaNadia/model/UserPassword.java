package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;

public class UserPassword {

	@NotEmpty(message = "La contraseña antigua no puede quedar vacía")
	private String oldPassword;
	@NotEmpty(message = "La nueva contraseña no puede quedar vacía")
	private String newPassword;
	@NotEmpty(message = "La confirmación de la contraseña no puede quedar vacía")
	private String confirmPassword;
	private String username;
	
	
	
	public UserPassword() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@Override
	public int hashCode() {
		return Objects.hash(oldPassword);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPassword other = (UserPassword) obj;
		return Objects.equals(oldPassword, other.oldPassword);
	}
	@Override
	public String toString() {
		return "UserPassword [oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", confirmPassword="
				+ confirmPassword + ", username=" + username + "]";
	}
	
	
	
}
