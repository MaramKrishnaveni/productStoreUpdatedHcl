package com.productStore.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Product;
import com.productStore.model.entities.Store;
import com.productStore.model.exceptions.ProductNotFoundException;
import com.productStore.model.persistance.ProductRepositery;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepositery repo;
	
	@Override
	public List<Product> findAll() {
		return repo.findAll();
	}

	@Override
	public List<Product> findByNameContaining(String name) {
		return repo.findByNameContaining(name);
	}

	@Override
	public List<Store> findAllStores(Long id) {
		Product product=repo.findById(id).orElseThrow(ProductNotFoundException::new);
		return product.getStores();
	}

	@Override
	public Product findById(Long id) {
		return repo.findById(id).orElseThrow(ProductNotFoundException::new);
	}

	@Override
	public Product findByName(String name) {
		return repo.findByName(name);
	}

	@Override
	public void update(Product product,Long id) {
		Product updateProduct=repo.findById(id).orElseThrow(ProductNotFoundException::new);
		repo.save(updateProduct);
	}

}
