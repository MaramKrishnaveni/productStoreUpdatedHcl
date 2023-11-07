package com.productStore.web.controller;

import java.security.Principal;
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
	
	@GetMapping(path="/products",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<List<Product>>(prodService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping(path="/products/{name}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getByNameContaining(@PathVariable(name="name")String name){
		return new ResponseEntity<List<Product>>(prodService.findByNameContaining(name),HttpStatus.OK);
	}

	@GetMapping(path="/products/{id}/stores",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Store>> getStoresOfProduct(@PathVariable(name="id")Long id){
		Product product=prodService.findById(id);
		return new ResponseEntity<List<Store>>(product.getStores(),HttpStatus.OK);
	}
	@GetMapping(path="/products/{id}/stores/{id1}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageRequest> buyConfirmation(@PathVariable(name="id")Long id,@PathVariable(name="id1")Long id1){
		Product product=prodService.findById(id);
		Store store=storeService.findById(id1);
		MessageRequest request=new MessageRequest("product: " +product.getName()+" of company name: "+product.getCompany()+" with price: "+product.getPrice()+" from store: "+store.getName()+" Login to proceed");
		return ResponseEntity.ok().body(request);
	}
	
	@GetMapping(path="/products/buy",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageRequest> placeOrder(@RequestParam(value="prodId")Long prodId,@RequestParam(value="storeId") Long storeId,Principal principle){
		
		Product product=prodService.findById(prodId);
		Store store=storeService.findById(storeId);
		Customer customer=custService.findByEmail(principle.getName());
		
		//find order
		Order order=new Order(product.getName(), product.getCompany(), customer.getName(), product.getPrice(), store.getName());
		customer.addOrder(order);
		order.setCustomer(customer);
		orderService.createOrder(order);
		
		MessageRequest req=new MessageRequest("order placed successfully please review");
		return ResponseEntity.ok().body(req);
	}
	
//	@GetMapping(path="/product/{id}/stores/{id1}/buy",produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<MessageRequest> placeOrder(Principal principle,@PathVariable(name="id")Long id,@PathVariable(name="id1")Long id1){
//		
//		Product product=prodService.findById(id);
//		Store store=storeService.findById(id1);
//		Customer customer=custService.findByEmail(principle.getName());
//		
//		//find order
//		Order order=new Order(product.getName(), product.getCompany(), customer.getName(), product.getPrice(), store.getName());
//		customer.addOrder(order);
//		order.setCustomer(customer);
//		orderService.createOrder(order);
//		
//		MessageRequest req=new MessageRequest("order placed successfully please review");
//		return ResponseEntity.ok().body(req);
//	}
	
	@PostMapping(path="/products/{id}/rating",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> productRating(Principal principle,@RequestBody RatingRequest req,@PathVariable(name="id")Long id){
		Product product=prodService.findById(id);
		int count=0;
		
		List<Order> orders=orderService.findAll();
		for(int i=0;i<orders.size();i++){
			if(orders.get(i).getProductName().equals(product.getName())){
				count=count+1;
			}
		}
		product.setRating(((product.getRating()*count)+req.getRating())/(count+1));
		prodService.update(product, id);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
	
}
