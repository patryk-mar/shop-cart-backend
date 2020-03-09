package com.patrykmaryn.shopping.cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patrykmaryn.shopping.cart.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
