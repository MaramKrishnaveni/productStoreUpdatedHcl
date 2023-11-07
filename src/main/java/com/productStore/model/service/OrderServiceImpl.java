package com.productStore.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Customer;
import com.productStore.model.entities.Order;
import com.productStore.model.exceptions.CustomerNotFoundException;
import com.productStore.model.persistance.CustomerRepositery;
import com.productStore.model.persistance.OrderRepositery;
import com.productStore.model.persistance.ProductRepositery;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepositery orderRepo;
	
	@Autowired
	private CustomerRepositery customerRepo;
	
	@Autowired
	private ProductRepositery productRepo;
	
	@Override
	public List<Order> findAll() {
		return orderRepo.findAll();
	}

	@Override
	public List<Order> findByCustomerId(Long id) {
		Customer customer=customerRepo.findById(id).orElseThrow(CustomerNotFoundException::new);
		return customer.getOrders();
	}

	@Override
	public void createOrder(Order order) {
		orderRepo.save(order);
	}

	@Override
	public List<Order> findByProductNameContaining(String name) {
		return orderRepo.findByProductNameContaining(name);
	}
	
	@Override
	public List<Order> findByCustomerName(String name) {
		return orderRepo.findByCustomerName(name);
	}


}
