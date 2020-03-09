package com.patrykmaryn.shopping.cart.dto;


public class OrderDto {
   
	private Long productId;
	private Long cartId;
	private boolean add;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCardId(Long cartId) {
		this.cartId = cartId;
	}
	public boolean isAdd() {
		return add;
	}
	public void setAdd(boolean add) {
		this.add = add;
	}

}
