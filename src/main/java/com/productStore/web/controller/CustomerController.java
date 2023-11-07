package com.productStore.web.controller;

import java.util.List;

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
	
	@GetMapping(path="/customers",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<List<Customer>>(custService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(path="/customers/{email}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getAllCustByEmail(@PathVariable(name="email")String email){
		return new ResponseEntity<Customer>(custService.findByEmail(email),HttpStatus.FOUND);
	}
	
	@PostMapping(path="/customers/register",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageRequest> register(@RequestBody RegRequest req){
		Customer customer=new Customer(req.getName(), req.getPassword(), req.getEmail(), req.getPhone(), req.getAddress(), "ROLE_CUSTOMER", true);
		custService.createCustomer(customer);
		MessageRequest request=new MessageRequest("registration successfull go to login");
		return ResponseEntity.ok().body(request);
	}
	
	
}
