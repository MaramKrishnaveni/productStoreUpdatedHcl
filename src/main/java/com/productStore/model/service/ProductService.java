package com.productStore.model.service;

import java.util.List;

import com.productStore.model.entities.Product;
import com.productStore.model.entities.Store;

public interface ProductService {

	public List<Product> findAll();
	public List<Product> findByNameContaining(String name);
	public Product findById(Long id);
	public List<Store> findAllStores(Long id);
	
	public Product findByName(String name);
	public void update(Product product,Long id);
}
