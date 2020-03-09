package com.patrykmaryn.shopping.cart.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patrykmaryn.shopping.cart.dto.CartDto;
import com.patrykmaryn.shopping.cart.dto.OrderDto;
import com.patrykmaryn.shopping.cart.dto.OrderProductDto;
import com.patrykmaryn.shopping.cart.entities.Cart;
import com.patrykmaryn.shopping.cart.repositories.CartRepository;
import com.patrykmaryn.shopping.cart.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
		
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;
	
	@PostMapping("/create-cart")
	public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {	
		Cart cart = new Cart();
		cartRepository.save(cart);
		cartDto.setId(cart.getId());
		return new ResponseEntity<> (cartDto, HttpStatus.OK);
	}
	
	@PostMapping("/update-cart-status")
	public void updateCartStatus(@RequestBody CartDto cartDto) {	
		Optional<Cart> cartOpt = cartRepository.findById(cartDto.getId());
		Cart cart = cartOpt.get();
		cart.setPaid(cartDto.isPaid());
		cartRepository.save(cart);
	}
	
	@PostMapping("/cart/{id}")
	public ResponseEntity<CartDto> displayCartById(
			@PathVariable @RequestBody Long id) {
		return cartService.displayCartById(id);
	}
	
	@PostMapping("/add-to-cart")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {	
		return cartService.createOrder(orderDto);
	}
	
	@PostMapping("/all/{id}")
	public ResponseEntity<List<OrderProductDto>> displayCartItems(
			@PathVariable @RequestBody Long id) {	
		return cartService.displayCartItem(id);
	}
	
	@PostMapping("/total/{id}")
	public Double displayTotal(
			@PathVariable  Long id) {
		return cartService.displayTotal(id);	
	}
}
