package com.productStore.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.productStore.model.exceptions.StoreNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.model.entities.Order;
import com.productStore.model.entities.Store;
import com.productStore.model.service.OrderService;
import com.productStore.model.service.StoreService;
import com.productStore.web.controller.requestBean.RatingRequest;

@RestController
@RequestMapping("/api")

public class StoreController {
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String timestamp = dateFormat.format(new Date());


	@Autowired
	private StoreService storeService;
	@Autowired
	private OrderService orderService;

	/**
	 * Retrieves a list of all stores.
	 *
	 * @return A list of store objects.
	 */
	@GetMapping(path = "/stores", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllStores() {
		logger.info("getStoreById call started : {}", timestamp);
		try {
			// Retrieve all stores from the database
			List<Store> stores = storeService.findAll();

			logger.info("getAllStores  call ended successfully : {}", timestamp);
			// Return a successful response with the list of stores
			return new ResponseEntity<>(stores, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("Error occurred at getAllStores Method:{}", e,timestamp);
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a store by its ID.
	 *
	 * @param id The ID of the store to retrieve.
	 * @return The store object if found, or a 404 response if not found.
	 */
	@GetMapping(path = "/stores/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStoreById(@PathVariable(name = "id") Long id) {
		logger.info("getStoreById call started : {}", timestamp);
		Optional<Store> store = storeService.findById(id);

		if (!store.isPresent()) {
			// Return 404 status code with an empty array in the response body
			String errorMessage = "Store Id not found.";
			logger.warn("getStoreById  not found : {}", timestamp);
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
		}
		logger.info("getStoreById call ended successfully : {}", timestamp);
		// Return a successful response with the store object
		return new ResponseEntity<>(store.get(), HttpStatus.OK);
	}

	/**
	 * Processes a rating request for a store.
	 *
	 * @param req The rating request object.
	 * @param id  The ID of the store to rate.
	 * @return A JSON response indicating the success of the rating request.
	 */
	@PostMapping(path = "/stores/{id}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productRating(@RequestBody RatingRequest req, @PathVariable(name = "id") Long id) {
		logger.info("productRating call started : {}", timestamp);
		try {
			Optional<Store> optionalStore = storeService.findById(id);

			if (optionalStore.isPresent()) {
				// Store found, update the rating
				Store store = optionalStore.get();
				double newRating = ((store.getRating() * store.getCount()) + req.getRating()) / (store.getCount() + 1);

				// Set the new rating and update the count
				store.setRating(newRating);
				store.setCount(store.getCount() + 1);

				// Save the updated store
				storeService.save(store);

				// Return a successful JSON response
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Thanks for your rating!");
				response.put("status", HttpStatus.OK.value());
				logger.info("productRating call ended successfully: {}", timestamp);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				// Store not found, create a new store
				logger.info("productRating call started for new store : {}", timestamp);

				Store newStore = new Store(/* provide necessary details for the new store */);
				newStore.setRating(req.getRating());
				newStore.setCount(1);

				// Save the new store
				storeService.save(newStore);

				// Return a successful JSON response
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Thanks for your rating!");
				response.put("status", HttpStatus.OK.value());
				logger.info("productRating call ended for new store successfully : {}", timestamp);

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			// Handle other exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", errorMessage);
			errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.error("productRating call ended  with error : {}", e,timestamp);

			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}


