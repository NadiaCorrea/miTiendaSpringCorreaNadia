package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Orders {
	@Id
	@Column(name="order_id")
	private int order_id;
	private LocalDateTime date;
	private double iva;
	@ManyToOne
	@JoinColumn (name="username", insertable = false, updatable = false) // name refers to the column on the db table
	private Users user;

	public Orders() {
		
	}
	
	public Orders(LocalDateTime date, double iva, Users user) throws OrdersException {
		super();
		setDate(date);
		setIva(iva);
		setUser(user);
		
	}
	

	public Orders(int order_id, LocalDateTime date, double iva, Users user) {
		super();
		this.order_id = order_id;
		this.date = date;
		this.iva = iva;
		
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) throws OrdersException {
		if(date == null) {
			throw new OrdersException("La fecha no puede ser nula.");
		} else {
			this.date = date;
		}	
		
	}

	public double getIva() {
		return iva;
	}

	public void setIva(double iva) throws OrdersException {
		if(iva < 0) {
			throw new OrdersException("El IVA no puede ser menor que 0.");
		} else {
			this.iva = iva;
		}
		
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) throws OrdersException {
		if (user == null) {
			throw new OrdersException("El usuario no puede ser nulo.");
		} else {
			this.user = user;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(order_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		return order_id == other.order_id;
	}

	@Override
	public String toString() {
		return "Orders [order_id=" + order_id + ", date=" + date + ", iva=" + iva + ", user=" + user + "]";
	}

	
	

}
