package com.productStore.model.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productStore.model.entities.Product;

@Repository
public interface ProductRepositery extends JpaRepository<Product, Long> {

	public List<Product> findAll();
	public List<Product> findByNameContaining(String name);
	
	public Product findByName(String name);
	
}
