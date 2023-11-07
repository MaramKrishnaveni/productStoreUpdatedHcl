package com.productStore.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="customer_new")
public class Customer {

	private static BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@JsonIgnore
	private String password;
	
	@Column(unique=true)
	private String email;
	
	private String phone;
	private String address;
	
	@JsonIgnore
	private String role;

	private boolean active;


	@OneToMany(mappedBy="customer", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Order> orders=new ArrayList<>();

	public Customer() {
	}

	public void addOrder(Order order){
		orders.add(order);
		order.setCustomer(this);
	}

	public Customer(String name, String password, String email, String phone,
			String address, String role, boolean active) {
	
		this.name = name;
		//this.password = password;
		setPassword(password);
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.active = active;
	}



	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public static BCryptPasswordEncoder getEncoder() {
		return encoder;
	}

	public static void setEncoder(BCryptPasswordEncoder encoder) {
		Customer.encoder = encoder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encoder.encode(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
