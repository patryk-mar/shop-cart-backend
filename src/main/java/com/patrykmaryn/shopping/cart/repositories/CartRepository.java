package com.patrykmaryn.shopping.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrykmaryn.shopping.cart.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
