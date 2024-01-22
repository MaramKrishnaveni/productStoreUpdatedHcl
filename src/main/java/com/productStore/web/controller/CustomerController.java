package com.productStore.web.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.productStore.model.entities.Customer;
import com.productStore.model.service.CustomerService;
import com.productStore.model.service.OrderService;
import com.productStore.model.service.ProductService;
import com.productStore.model.service.StoreService;
import com.productStore.web.controller.requestBean.MessageRequest;
import com.productStore.web.controller.requestBean.RegRequest;

@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	private CustomerService custService;
	
	@Autowired
	private ProductService prodService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderService orderService;

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String timestamp = dateFormat.format(new Date());


	/**
	 * Registers a new customer.
	 *
	 * @param req The registration request containing customer details.
	 * @return A message indicating the success of the registration.
	 */
	@PostMapping(path = "/customers/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody RegRequest req) {
		logger.info("register call started : {}", timestamp);
		try {
			// Create a new customer
			Customer customer = new Customer(req.getName(), req.getPassword(), req.getEmail(), req.getPhone(),
					req.getAddress(), "ROLE_CUSTOMER", true);

			// Save the customer to the database
			custService.createCustomer(customer);

			// Return a successful response with a message
			MessageRequest request = new MessageRequest("Registration successful. Go to login.");
			logger.info("register call ended successfully : {}", timestamp);

			return ResponseEntity.ok().body(request);
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("register call has error  : {}",e, timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a list of all customers.
	 *
	 * @return A list of customer objects.
	 */
	@GetMapping(path = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCustomers() {
		logger.info("getAllCustomers call started : {}", timestamp);

		try {
			// Retrieve all customers from the database
			List<Customer> customers = custService.findAll();
			logger.info("getAllCustomers call has ended successfully : {}", timestamp);

			// Return a successful response with the list of customers
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("getAllCustomers call has error : {}",e, timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a customer by email.
	 *
	 * @param email The email address of the customer to retrieve.
	 * @return The customer object if found, or an error message if not found.
	 */
	@GetMapping(path = "/customers/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCustByEmail(@RequestParam(name = "email") String email) {
		logger.info("getAllCustByEmail call has started  : {}", timestamp);

		try {
			// Find customer by email
			Customer customer = custService.findByEmail(email);

			if (customer != null) {
				// Return a successful response with the customer object
				logger.info("getAllCustByEmail call has ended successfully : {}", timestamp);

				return new ResponseEntity<>(customer, HttpStatus.OK);
			} else {
				// Handle the case when the customer with the given email is not found
				String errorMessage = "Email ID " + email + " not found.";
				logger.warn("getAllCustByEmail the email is not found : {}", timestamp);

				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.info("getAllCustByEmail call has error : {}",e, timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





}
