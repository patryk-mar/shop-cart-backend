package com.patrykmaryn.shopping.cart.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patrykmaryn.shopping.cart.dto.ProductDto;
import com.patrykmaryn.shopping.cart.entities.Product;
import com.patrykmaryn.shopping.cart.repositories.CartRepository;
import com.patrykmaryn.shopping.cart.repositories.OrderProductRepository;
import com.patrykmaryn.shopping.cart.repositories.ProductRepository;
import com.patrykmaryn.shopping.cart.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<ProductDto>> displayRecipients() {		
		List<ProductDto> list = new ArrayList<>();
		list = productService.showAllProducts();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/add-product")
	public void displayRecipients2() {
				
		Product product1 = new Product();
		product1.setName("T-shirt 1");
		product1.setDescription("This is new t-shirt");
		product1.setImageUrl("https://maryn-ecommerce-new.s3.eu-west-2.amazonaws.com/media/products/t-shirt1.jpeg");
		product1.setPrice(22.22);
		
		Product product2 = new Product();
		product2.setName("T-shirt 2");
		product2.setDescription("This is new t-shirt");
		product2.setImageUrl("https://maryn-ecommerce-new.s3.eu-west-2.amazonaws.com/media/products/t-shirt2.jpg");
		product2.setPrice(32.22);
		
		Product product3 = new Product();
		product3.setName("T-shirt 3");
		product3.setDescription("This is new t-shirt");
		product3.setImageUrl("https://maryn-ecommerce-new.s3.eu-west-2.amazonaws.com/media/products/t-shirt3.jpg");
		product3.setPrice(35.22);
		productRepository.save(product1);
		productRepository.save(product2);
		productRepository.save(product3);
	}

}
