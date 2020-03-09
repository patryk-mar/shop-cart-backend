package com.patrykmaryn.shopping.cart.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrykmaryn.shopping.cart.entities.Cart;
import com.patrykmaryn.shopping.cart.entities.OrderProduct;
import com.patrykmaryn.shopping.cart.entities.Product;


public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

	public OrderProduct findByCart(Cart cart);
	public boolean existsByCartAndProduct(Cart cart, Product product);
	public OrderProduct findByCartAndProduct(Cart cart, Product product);
	public List<OrderProduct> findAllByCartId(Long id);
}
