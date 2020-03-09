package com.patrykmaryn.shopping.cart.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	
	@Id
	private String id;
	
	private String stripeId;
	
	@OneToMany(cascade=CascadeType.ALL)
    private List<Cart> orders = new ArrayList<Cart>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStripeId() {
		return stripeId;
	}

	public void setStripeId(String stripeId) {
		this.stripeId = stripeId;
	}

	public List<Cart> getOrders() {
		return orders;
	}

	public void setOrders(List<Cart> orders) {
		this.orders = orders;
	}
	
	

}
