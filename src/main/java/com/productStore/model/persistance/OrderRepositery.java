package com.productStore.model.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productStore.model.entities.Order;

@Repository
public interface OrderRepositery extends JpaRepository<Order, Long> {

	public List<Order> findAll();
	public List<Order> findByCustomerId(Long id);
	
	public List<Order> findByProductNameContaining(String name);
	
	public List<Order> findByCustomerName(String name);
}
