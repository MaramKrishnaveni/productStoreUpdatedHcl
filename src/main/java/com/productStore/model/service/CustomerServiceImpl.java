package com.productStore.model.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productStore.model.entities.Customer;
import com.productStore.model.exceptions.CustomerNotFoundException;
import com.productStore.model.persistance.CustomerRepositery;
import com.productStore.model.persistance.ProductRepositery;

@Service
@Transactional

public class CustomerServiceImpl implements CustomerService  {

	private static final Logger log =  LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepositery repo;
	
	@Autowired
	private ProductRepositery productRepo;

	@Override
	public Customer findByEmail(String email) {
		try {
			// Decode the URL-encoded email parameter
			String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8.toString());

			log.info("Received email parameter: {}", decodedEmail);
			// Query the repository with the decoded email
			Customer customer = repo.findByEmail(decodedEmail);
			log.info("Result from repository query: {}", customer);

			return customer;
		} catch (UnsupportedEncodingException e) {
			// Handle the decoding exception
			log.error("Error decoding email parameter", e);

			return null;
		}
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
