package com.productStore.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="store_new")
public class Store {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String phone;
	private String address;
	private double rating;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	    @JoinTable(name = "store_tab_product_tab",
	        joinColumns =@JoinColumn(name = "store_id"),
	        inverseJoinColumns = @JoinColumn(name = "product_id")
	    )
//	@JoinColumn(name="product_id")
//	@JoinColumn(name="store_id")
	private List<Product> products=new ArrayList<Product>();
	
	public Store() {
	}

	public Store(String name, String phone, String address, double rating) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.rating = rating;
	}

	public Store(String name, String phone, String address, double rating,
			List<Product> products) {
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.rating = rating;
		this.products = products;
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
	
	
}
