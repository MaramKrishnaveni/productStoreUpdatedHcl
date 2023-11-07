package com.productStore.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Customer;
import com.productStore.model.exceptions.CustomerNotFoundException;
import com.productStore.model.persistance.CustomerRepositery;
import com.productStore.model.persistance.ProductRepositery;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	private CustomerRepositery repo;
	
	@Autowired
	private ProductRepositery productRepo;

	@Override
	public Customer findByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public List<Customer> findAll() {
		return repo.findAll();
	}

	@Override
	public Customer findById(Long id) {
		return repo.findById(id).orElseThrow(CustomerNotFoundException::new);
	}

	@Override
	public void createCustomer(Customer customer) {
		repo.save(customer);
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}
	

	
	
}
