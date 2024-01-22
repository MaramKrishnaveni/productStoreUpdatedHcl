package com.productStore.web.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.model.entities.Customer;
import com.productStore.model.entities.Order;
import com.productStore.model.entities.Product;
import com.productStore.model.entities.Store;
import com.productStore.model.service.CustomerService;
import com.productStore.model.service.OrderService;
import com.productStore.model.service.ProductService;
import com.productStore.model.service.StoreService;
import com.productStore.web.controller.requestBean.MessageRequest;
import com.productStore.web.controller.requestBean.RatingRequest;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService prodService;
	
	@Autowired
	private CustomerService custService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderService orderService;

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String timestamp = dateFormat.format(new Date());

	/**
	 * Retrieves a list of all products.
	 *
	 * @return A list of product objects.
	 */
	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllProducts() {
		logger.info("getAllProducts call started : {}", timestamp);

		try {
			// Retrieve all products from the database
			List<Product> products = prodService.findAll();
			logger.info("getAllProducts call ended successfully: {}", timestamp);

			// Return a successful response with the list of products
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("getAllProducts call has error  : {}", e,timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves products by name containing a specified string.
	 *
	 * @param name The string to filter products by name.
	 * @return A list of product objects containing the specified string in their names.
	 */
	@GetMapping(path = "/products/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getByNameContaining(@PathVariable(name = "name") String name) {
		logger.info("getByNameContaining call started : {}", timestamp);

		try {
			// Find products by name containing the specified string
			List<Product> products = prodService.findByNameContaining(name);

			if (!products.isEmpty()) {
				// Return a successful response with the list of products
				logger.info("getByNameContaining call ended successfully : {}", timestamp);

				return new ResponseEntity<>(products, HttpStatus.OK);
			} else {
				// Handle the case when no products with the given name are found
				logger.warn("getByNameContaining call no product found  : {}", timestamp);

				String errorMessage = "No products found with name containing: " + name;
				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("getByNameContaining call has error : {}",e, timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves stores associated with a specific product.
	 *
	 * @param id The ID of the product.
	 * @return A list of store objects associated with the specified product.
	 */
	@GetMapping(path = "/products/{id}/stores", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStoresOfProduct(@PathVariable(name = "id") Long id) {
		logger.info("getStoresOfProduct call started : {}", timestamp);

		try {
			// Find the product by ID
			Optional<Product> product = prodService.findById(id);

			if (product.isPresent()) {
				// Create a new store and add it to the list
				Optional<Store> storeDb = storeService.findById(id);
				Store store = new Store();
				store.setId(storeDb.get().getId());
				store.setRating(storeDb.get().getRating());
				store.setName(storeDb.get().getName());
				store.setPhone(storeDb.get().getPhone());
				store.setCount(storeDb.get().getCount());

				List<Store> listStore = new ArrayList<>();
				listStore.add(store);

				// Set the list of stores for the product
				product.get().setStores(listStore);

				// Get the list of stores from the product
				List<Store> stores = product.get().getStores();

				// Log information for debugging
				logger.info("Product ID: " + id + ", Number of Stores: " + stores.size());

				// Return a successful response with the list of stores
				logger.info("getStoresOfProduct call ended successfully : {}", timestamp);

				return new ResponseEntity<>(stores, HttpStatus.OK);
			} else {
				// Log information for debugging
			logger.info("Product with ID " + id + " not found.");

				// Handle the case when the product with the given ID is not found
				String errorMessage = "Product with ID " + id + " not found.";
				logger.warn("getStoresOfProduct call with ID not found  : {}", timestamp);

				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Log the exception for debugging
			e.printStackTrace();

			// Handle other exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("getStoresOfProduct call has error  : {}", e,timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves a message for buying confirmation.
	 *
	 * @param id  The ID of the product.
	 * @param id1 The ID of the store.
	 * @return A message indicating details for buying confirmation.
	 */
	@GetMapping(path = "/products/{id}/stores/{id1}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buyConfirmation(@PathVariable(name = "id") Long id, @PathVariable(name = "id1") Long id1) {
		logger.info("buyConfirmation call started : {}", timestamp);

		try {
			// Find product and store by IDs
			Optional<Product> product = prodService.findById(id);
			Optional<Store> store = storeService.findById(id1);

			if (product.isPresent() && store.isPresent()) {
				// Return a message with details for buying confirmation
				MessageRequest request = new MessageRequest("Product: " + product.get().getName() +
						" of company name: " + product.get().getCompany() +
						" with price: " + product.get().getPrice() +
						" from store: " + store.get().getName() +
						" Login to proceed");
				logger.info("buyConfirmation call ended successfully : {}", timestamp);

				return ResponseEntity.ok().body(request);
			} else {
				// Handle the case when either the product or the store with the given IDs is not found
				String errorMessage = "Product or Store with given IDs not found.";
				logger.warn("buyConfirmation call has no store or product Id : {}", timestamp);

				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("buyConfirmation call has error  : {}", e,timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Places an order for a product from a specific store.
	 *
	 * @param id    The ID of the product.
	 * @param id1   The ID of the store.
	 * @param email The email of the customer placing the order.
	 * @return A message indicating the success or failure of placing the order.
	 */
	@PostMapping(path = "/products/{id}/stores/{id1}/order", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> placeOrder(@PathVariable(name = "id") Long id, @PathVariable(name = "id1") Long id1,
										@RequestParam(name = "email") String email) {
		logger.info("placeOrder call started : {}", timestamp);

		try {
			// Find product and store by IDs
			Optional<Product> product = prodService.findById(id);
			Optional<Store> store = storeService.findById(id1);

			if (product.isPresent() && store.isPresent()) {
				// Assuming you have access to the customer based on the principal
				Customer customer = custService.findByEmail(email);

				if (customer != null) {
					// Create an order and associate it with the customer
					Order order = new Order(product.get().getName(), product.get().getCompany(),
							customer.getName(), product.get().getPrice(), store.get().getName());

					customer.addOrder(order);
					order.setCustomer(customer);
					orderService.createOrder(order);

					// Return a message indicating the success of placing the order
					MessageRequest req = new MessageRequest("Order placed successfully. Please review.");
					logger.info("placeOrder call ended successfully : {}", timestamp);

					return ResponseEntity.ok().body(req);
				} else {
					// Handle the case when the customer is not found
					String errorMessage = "Customer not found.";
					logger.warn("placeOrder call has no requested customer : {}", timestamp);

					return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
				}
			} else {
				// Handle the case when either the product or the store with the given IDs is not found
				String errorMessage = "Product or Store with given IDs not found.";
				logger.warn("placeOrder call has no Product or Store with given IDs : {}", timestamp);

				return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// Handle exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			logger.error("placeOrder call ended with error : {}",e, timestamp);

			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Rates a product based on the user's input.
	 *
	 * @param principle The principal representing the authenticated user.
	 * @param req       The rating request containing the user's rating.
	 * @param id        The ID of the product.
	 * @return A message indicating the success or failure of rating the product.
	 */
	@PostMapping(path = "/products/{id}/rating", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> productRating(Principal principle, @RequestBody RatingRequest req,
										   @PathVariable(name = "id") Long id) {
		logger.info("productRating call started : {}", timestamp);

		try {
			// Find the product by ID
			Optional<Product> optionalProduct = prodService.findById(id);

			if (optionalProduct.isPresent()) {
				// Product found, update the rating
				Product product = optionalProduct.get();

				int count = 0;

				List<Order> orders = orderService.findAll();
				for (Order order : orders) {
					if (order.getProductName().equals(product.getName())) {
						count = count + 1;
					}
				}

				product.setRating(((product.getRating() * count) + req.getRating()) / (count + 1));
				prodService.update(Optional.of(product), id);

				// Return a successful JSON response with a custom message
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Thanks for your rating!");
				response.put("status", HttpStatus.ACCEPTED.value());
				logger.info("productRating call ended successfully : {}", timestamp);

				return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			} else {
				// Product not found, create a new product
				logger.info("productRating call for new product started : {}", timestamp);

				Product newProduct = new Product(/* provide necessary details for the new product */);
				newProduct.setRating(req.getRating());

				// Save the new product
				prodService.save(newProduct);

				// Return a successful JSON response with a custom message
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Thanks for your rating!");
				response.put("status", HttpStatus.ACCEPTED.value());
				logger.info("productRating call for new product ended successfully : {}", timestamp);

				return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			// Handle other exceptions (500 Internal Server Error)
			String errorMessage = "Internal Server Error";
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", errorMessage);
			errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			logger.info("productRating call  has error : {}", e,timestamp);

			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
