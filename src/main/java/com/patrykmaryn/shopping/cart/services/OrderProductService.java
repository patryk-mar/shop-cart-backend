package com.patrykmaryn.shopping.cart.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patrykmaryn.shopping.cart.dto.OrderProductDto;
import com.patrykmaryn.shopping.cart.entities.OrderProduct;
import com.patrykmaryn.shopping.cart.repositories.OrderProductRepository;

@Service
public class OrderProductService {
	
	@Autowired
	OrderProductRepository orderProductRepository;
    
	public List<OrderProductDto> showAllCartItems(Long id) {
	    try {
		    List<OrderProduct> orderProducts = orderProductRepository.findAllByCartId(id);
			  return orderProducts
				.stream()
				.map(this::mapFromOrderProductToDto).collect(Collectors.toList());
			  
		      } catch (Exception e) {
			         return Collections.emptyList();
		      }
		}
	
	private OrderProductDto mapFromOrderProductToDto(OrderProduct product) {
		OrderProductDto productDto = new OrderProductDto();
        productDto.setProductName(product.getProduct().getName());
        productDto.setProductPrice(product.getProduct().getPrice());
        productDto.setQuantity(product.getQuantity());
        productDto.setProductId(product.getProduct().getId());
		return productDto;
	 }
}
