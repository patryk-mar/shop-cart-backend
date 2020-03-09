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
	
}
