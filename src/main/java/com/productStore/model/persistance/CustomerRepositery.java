package com.productStore.model.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productStore.model.entities.Customer;

@Repository
public interface CustomerRepositery extends JpaRepository<Customer, Long> {

	public Customer findByEmail(String email);
	public List<Customer> findAll();
	public Optional<Customer> findById(Long id);
	
	//public void createCustomer(Customer customer);
	public void deleteById(Long id);
}
