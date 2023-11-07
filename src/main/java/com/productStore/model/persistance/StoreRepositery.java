package com.productStore.model.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productStore.model.entities.Store;

@Repository
public interface StoreRepositery extends JpaRepository<Store, Long> {

	public List<Store> findAll();
	public List<Store> findAllById(Long id);
}
