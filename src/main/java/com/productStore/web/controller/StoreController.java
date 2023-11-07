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

import com.productStore.model.entities.Order;
import com.productStore.model.entities.Store;
import com.productStore.model.service.OrderService;
import com.productStore.model.service.StoreService;
import com.productStore.web.controller.requestBean.RatingRequest;

@RestController
@RequestMapping("/api")
public class StoreController {

	@Autowired
	private StoreService storeService;
	@Autowired
	private OrderService orderService;
	
	@GetMapping(path="/stores",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Store>> getAllStores(){
		return new ResponseEntity<List<Store>>(storeService.findAll(),HttpStatus.FOUND);
	}
	
	@GetMapping(path="/stores/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Store> getStoreById(@PathVariable(name="id")Long id){
		return new ResponseEntity<Store>(storeService.findById(id),HttpStatus.FOUND);
	}
	
	@PostMapping(path="/stores/{id}/rating",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> productRating(@RequestBody RatingRequest req,@PathVariable(name="id")Long id){
		Store store=storeService.findById(id);
		int count1=0;
		//order
		List<Order> orders=orderService.findAll();
		
		for(int i=0;i<orders.size();i++){
			if(orders.get(i).getStoreName().equals(store.getName())){
				count1=count1+1;
			}
		}
		
		store.setRating(((store.getRating()*count1)+req.getRating())/(count1+1));
			
		
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		
	}
}
