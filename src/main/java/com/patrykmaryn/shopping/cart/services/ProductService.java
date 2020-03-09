package com.patrykmaryn.shopping.cart.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrykmaryn.shopping.cart.dto.ProductDto;
import com.patrykmaryn.shopping.cart.entities.Product;
import com.patrykmaryn.shopping.cart.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<ProductDto> showAllProducts() {
	    try {
		    List<Product> products = productRepository.findAll();
				 return products
					.stream()
					.map(this::mapFromUserToDto).collect(Collectors.toList());
			} catch (Exception e) {
				 return Collections.emptyList();
			}
		}
	
    private ProductDto mapFromUserToDto(Product product) {
    	
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setPrice(product.getPrice());
		return productDto;
	 }
}
