package com.patrykmaryn.shopping.cart.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.patrykmaryn.shopping.cart.dto.CartDto;
import com.patrykmaryn.shopping.cart.dto.OrderDto;
import com.patrykmaryn.shopping.cart.dto.OrderProductDto;
import com.patrykmaryn.shopping.cart.entities.Cart;
import com.patrykmaryn.shopping.cart.entities.OrderProduct;
import com.patrykmaryn.shopping.cart.entities.Product;
import com.patrykmaryn.shopping.cart.repositories.CartRepository;
import com.patrykmaryn.shopping.cart.repositories.OrderProductRepository;
import com.patrykmaryn.shopping.cart.repositories.ProductRepository;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	@Autowired
	OrderProductService orderProductService;
		
	@Autowired
	CartDto cartDto;
	
	public ResponseEntity<CartDto> displayCartById(Long id) {
	    try {
			Optional<Cart> cardOpt = cartRepository.findById(id);
			if(cardOpt.isPresent()) {
				Cart cart = cardOpt.get();
				cartDto.setId(cart.getId());
				cartDto.setItems(cart.getItems());
				
				Double total = convertToDecimal(cart.getTotalOrderPrice());		
				cartDto.setTotal(total);
				//cartDto.setTotal(cart.getTotalOrderPrice());
				cartDto.setUser(cart.getUser());
			    return new ResponseEntity<> (cartDto, HttpStatus.OK);
		    }
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public ResponseEntity<OrderDto> createOrder(OrderDto orderDto){
		Optional<Product> productOpt = productRepository.findById(orderDto.getProductId());
		Optional<Cart> cartOpt = cartRepository.findById(orderDto.getCartId());
		
		boolean orderExists = orderProductRepository.existsByCartAndProduct(cartOpt.get(),
				productOpt.get());
		
		try {
			if(!orderExists && orderDto.isAdd()) {
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setCart(cartOpt.get());
			orderProduct.setProduct(productOpt.get()); 
			orderProduct.setQuantity(1);
			orderProductRepository.save(orderProduct);
		} else {
			OrderProduct orderProduct = orderProductRepository.
					findByCartAndProduct(cartOpt.get(), productOpt.get());
			Integer quantity = orderProduct.getQuantity();
			if(orderDto.isAdd())
				quantity = quantity + 1;
			else
				quantity = quantity - 1;
			orderProduct.setQuantity(quantity);
			orderProductRepository.save(orderProduct);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<> (orderDto, HttpStatus.OK);
	}
	
	public ResponseEntity<List<OrderProductDto>> displayCartItem(Long id){
		List<OrderProductDto> list = new ArrayList<>();
		list = orderProductService.showAllCartItems(id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	public Double displayTotal(Long id) {
		Optional<Cart> cartOpt = cartRepository.findById(id);
		Cart cart = cartOpt.get();
		Double total = cartOpt.get().getTotalOrderPrice();
		cart.setTotal(total);
		cartRepository.save(cart);
		return convertToDecimal(total);
	}
	
	public Double convertToDecimal(Double total) {
		BigDecimal bd = new BigDecimal(Double.toString(total));
		total = bd.setScale(2, RoundingMode.HALF_UP)
		        .doubleValue();
		return total;
	}

}
