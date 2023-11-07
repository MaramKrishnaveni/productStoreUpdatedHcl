package com.productStore.model.service;

import java.util.List;

import com.productStore.model.entities.Customer;

public interface CustomerService {

//	public List<Customer> findAll();
//	public Customer findById(Long id);
//	public void createCustomer(Customer customer);
//	public void deleteCustomerById(long id);
//	
//	public void update(long id,Customer customer);
//	public List<Customer> findByNameContaining(String name);
//	
////	public List<Customer> findAllByProductName(String name);
	
	public Customer findByEmail(String email);
	public List<Customer> findAll();
	public Customer findById(Long id);
	
	public void createCustomer(Customer customer);
	public void deleteById(Long id);
}
