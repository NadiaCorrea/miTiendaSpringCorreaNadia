package com.jacaranda.miTiendaSpringCorreaNadia.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Orders {
	@Id 
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private LocalDateTime date;
	private double iva;
	
	@ManyToOne
	@JoinColumn (name="username") // name refers to the column on the db table
	private Users user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ElementOrder> elementOrder = new ArrayList<>();

	public Orders() {
		
	}
	
	public Orders(LocalDateTime date, double iva, Users user) throws OrdersException {
		super();
		setDate(date);
		setIva(iva);
		setUser(user);
		
	}


	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) throws OrdersException {
		if(date == null) {
			throw new OrdersException("La fecha no puede ser nula.");
		} else {
			if(date.isAfter(LocalDateTime.now())) {
				throw new OrdersException("La fecha de venta no puede ser superior a la fecha actual.");
			} else {
				this.date = date;
			}
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

	public List<ElementOrder> getElementOrder() {
		return elementOrder;
	}

	public void setElementOrder(List<ElementOrder> elementOrder) {
		this.elementOrder = elementOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId);
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
		return orderId == other.orderId;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", date=" + date + ", iva=" + iva + ", user=" + user + "]";
	}

	
	

}
