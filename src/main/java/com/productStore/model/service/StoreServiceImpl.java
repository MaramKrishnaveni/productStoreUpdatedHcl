package com.productStore.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Store;
import com.productStore.model.exceptions.StoreNotFoundException;
import com.productStore.model.persistance.StoreRepositery;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepositery repo;

	@Override
	public List<Store> findAll() {
		return repo.findAll();
	}

	@Override
	public List<Store> findAllById(Long id) {
		return repo.findAllById(id);
	}

	@Override
	public Store findById(Long id) {
		return repo.findById(id).orElseThrow(StoreNotFoundException::new);
	}
	
	
}
