package com.productStore.model.service;

import java.util.List;

import com.productStore.model.entities.Store;

public interface StoreService {
	public List<Store> findAll();
	public Store findById(Long id);
	public List<Store> findAllById(Long id);
}
