package com.productStore.model.service;

import java.util.List;

import com.productStore.model.entities.Order;

public interface OrderService {

	public List<Order> findAll();
	public List<Order> findByCustomerId(Long id);
	
	public void createOrder(Order order);
	public List<Order> findByProductNameContaining(String name);
	
	public List<Order> findByCustomerName(String name);
	
}
