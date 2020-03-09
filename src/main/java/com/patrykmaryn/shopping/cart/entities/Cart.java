package com.patrykmaryn.shopping.cart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.stereotype.Controller;

@Entity
@Controller	
public class Cart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String user;
	private String items;
	private Double total;
	private boolean paid;
	
	
	@OneToMany(mappedBy = "cart", cascade=CascadeType.ALL)
    private List<OrderProduct> products = new ArrayList<OrderProduct>();
	
	@Transient
    public Double getTotalOrderPrice() {
	    double sum = 0D;
	    List<OrderProduct> orderProducts = getOrderProduct();
	    for (OrderProduct op : orderProducts) {
	        sum += op.getTotalPrice();
	    }
	    return sum;
    }
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public Double getTotal() {
		return getTotalOrderPrice();
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	public List<OrderProduct> getOrderProduct(){
		return products;
	}
	
	public void setOrderProduct(List<OrderProduct> products) {
		this.products = products;
	}
	
	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
}
