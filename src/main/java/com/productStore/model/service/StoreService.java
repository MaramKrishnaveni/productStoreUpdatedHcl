package com.productStore.model.service;

import java.util.List;
import java.util.Optional;

import com.productStore.model.entities.Store;

public interface StoreService {
	public List<Store> findAll();
	public Optional<Store> findById(Long id);
	public List<Store> findAllById(Long id);

	Store save(Store store);

}
