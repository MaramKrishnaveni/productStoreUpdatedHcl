package com.productStore.model.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product_new")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String company;
	private double price;
	@JsonIgnore
	private int qty;
	private double rating;
	private String[] specs;
	
	@ManyToMany(mappedBy = "products", cascade =CascadeType.ALL,fetch=FetchType.EAGER)
	@JsonIgnore
//	@JoinColumn(name="product_fk")
//	@JoinColumn(name="store_fk")
	private List<Store> stores=new ArrayList<Store>();

	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String name, String company, double price, int qty,
			double rating, String[] specs) {
		this.name = name;
		this.company = company;
		this.price = price;
		this.qty = qty;
		this.rating = rating;
		this.specs = specs;
	}
	
	public Product(String name, String company, double price, int qty,
			double rating, String[] specs, List<Store> stores) {
	
		this.name = name;
		this.company = company;
		this.price = price;
		this.qty = qty;
		this.rating = rating;
		this.specs = specs;
		this.stores = stores;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String[] getSpecs() {
		return specs;
	}

	public void setSpecs(String[] specs) {
		this.specs = specs;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}
	
	
		
}
