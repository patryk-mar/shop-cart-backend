package com.patrykmaryn.shopping.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrykmaryn.shopping.cart.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{

}
