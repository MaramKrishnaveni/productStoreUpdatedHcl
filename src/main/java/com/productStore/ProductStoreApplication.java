package com.productStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.productStore.model.persistance.ProductRepositery;
import com.productStore.model.persistance.StoreRepositery;
import com.productStore.model.service.ProductService;

@SpringBootApplication
public class ProductStoreApplication implements CommandLineRunner {

	
	@Autowired
	private ProductRepositery prodrepo;
	@Autowired
	private StoreRepositery storerepo;
	@Autowired
	private ProductService service;	
	
	public static void main(String[] args) {
		SpringApplication.run(ProductStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
//		List<Store> list=service.findById(1L).getStores();
//		
//		for(int i=0;i<list.size();i++){
//		    System.out.println(list.get(i));
//		}
				
//		Store store1=new Store("raj store", "99499", "bangalore", 4.5);
//		Store store2=new Store("king store", "9989", "chennai", 4.4);
//		
//		Product product1=new Product("pen", "PARKER", 200.0, 20, 5.0, new String[]{"writing","style","status","poem"});
//		Product product2=new Product("phone", "SAMSUNG", 10000, 20, 4.7, new String[]{"calling","searching","connection","4Gb RAM, 128Gb ROM"});
//		Product product3=new Product("psp", "RAJ", 2000, 50, 4.6, new String[]{"games","fun","connection"});
//		Product product4=new Product("light", "SERIES", 5000, 50, 5.0, new String[]{"light","searching","connection","3hr backup"});
//		
//		
//		store1.getProducts().add(product1);
//		store1.getProducts().add(product2);
//		store1.getProducts().add(product3);
//		
//		store2.getProducts().add(product2);
//		store2.getProducts().add(product3);
//		store2.getProducts().add(product4);
//		
//		product1.getStores().add(store1);
//		
//		product2.getStores().add(store1);
//		product2.getStores().add(store2);
//		
//		product3.getStores().add(store1);
//		product3.getStores().add(store2);
//		
//		product4.getStores().add(store2);
//	
//		
//		prodrepo.save(product1);
//		prodrepo.save(product2);
//		prodrepo.save(product3);
//		prodrepo.save(product4);
//		
//		
//		storerepo.save(store1);
//		storerepo.save(store2);
		
		
	}

}
