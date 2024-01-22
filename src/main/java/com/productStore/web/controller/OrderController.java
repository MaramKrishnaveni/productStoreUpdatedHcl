package com.productStore.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.model.entities.Order;
import com.productStore.model.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * Retrieves a list of all orders.
	 *
	 * @return A list of order objects.
	 */
	@GetMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllOrders() {
		try {
			// Retrieve all orders from the database
			List<Order> orders = orderService.findAll();

			// Return a successful response with the list of orders
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves orders by product name.
	 *
	 * @param name The name of the product to filter orders.
	 * @return A list of order objects for the specified product.
	 */
	@GetMapping(path = "/orders/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllOrdersByProduct(@PathVariable(name = "name") String name) {
		try {
			// Find orders by product name
			List<Order> orders = orderService.findByProductNameContaining(name);

			if (!orders.isEmpty()) {
				// Return a successful response with the list of orders
				return new ResponseEntity<>(orders, HttpStatus.OK);
			} else {
				// Handle the case when no orders are found for the specified product
				String errorMessage = "No orders found for product name: " + name;
				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves orders by customer name.
	 *
	 * @param name The name of the customer to filter orders.
	 * @return A list of order objects for the specified customer.
	 */
	@GetMapping(path = "/orders/customers/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllOrdersByCustomer(@PathVariable(name = "name") String name) {
		try {
			// Find orders by customer name
			List<Order> orders = orderService.findByCustomerName(name);

			if (!orders.isEmpty()) {
				// Return a successful response with the list of orders
				return new ResponseEntity<>(orders, HttpStatus.OK);
			} else {
				// Handle the case when no orders are found for the specified customer
				String errorMessage = "No orders found for customer name: " + name;
				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
