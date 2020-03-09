package com.patrykmaryn.shopping.cart.services;

import com.google.gson.annotations.SerializedName;

public class StripeService {

	// this class is if we want to setup order payload on client side
	public static class CreatePaymentBody{
		@SerializedName("items")
		Object[] items;
		
		@SerializedName("currency")
		String currency;
		
		@SerializedName("cartId")
		Long cartId;
		
		public Object[] getItems() {
			return items;
		}
		
		public String getCurrency() {
			return currency;
		}
		
		public Long getCartId() {
			return cartId;
		}
		
	}
	
	public static class CreatePaymentResponse {
		private String publishableKey;
		private String clientSecret;
		
		public CreatePaymentResponse(String publishableKey,
				String clientSecret) {
			this.publishableKey = publishableKey;
			this.clientSecret = clientSecret;
		}	
	}
	
	// to test stripe amount
	public static int calculateOrderAmount(Object[] items) {
		return 1400;
	}
	
}


