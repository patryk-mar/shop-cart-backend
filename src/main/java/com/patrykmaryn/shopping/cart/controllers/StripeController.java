package com.patrykmaryn.shopping.cart.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.patrykmaryn.shopping.cart.dto.CartDto;
import com.patrykmaryn.shopping.cart.entities.Cart;
import com.patrykmaryn.shopping.cart.entities.User;
import com.patrykmaryn.shopping.cart.repositories.CartRepository;
import com.patrykmaryn.shopping.cart.repositories.UserRepository;
import com.patrykmaryn.shopping.cart.services.StripeService.CreatePaymentBody;
import com.patrykmaryn.shopping.cart.services.StripeService.CreatePaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
public class StripeController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@PostMapping("/create-payment-intent")
	public String createPaymentIntent(HttpServletRequest request, HttpServletResponse resposne) 
			throws StripeException { 
		
		Gson gson = new Gson();
		Stripe.apiKey = "sk_test_XXXXXXXXXXXXXXXXXX";				
			resposne.setContentType("application/json");
			
			try {
				StringBuilder buffer = new StringBuilder();
			    BufferedReader reader = request.getReader();
			    String line;
			    while ((line = reader.readLine()) != null) {
			        buffer.append(line);
			    }
			    String dataBody = buffer.toString();
				CreatePaymentBody postBody = gson.fromJson(dataBody, CreatePaymentBody.class);
				
				Long cartId = postBody.getCartId();
				Optional<Cart> cartOpt = cartRepository.findById(cartId);
				Cart cart = cartOpt
						.orElseThrow(() ->
		                new UsernameNotFoundException("Cart not found"));
				
				String userId = cart.getUser();
				Double total = cart.getTotalOrderPrice();
				Double totalStripe = total * 100;
				Long amount = Math.round(totalStripe);
				
				Optional<User> customerOpt = userRepository.findById(userId);
				User user = customerOpt
						.orElseThrow(() ->
		                new UsernameNotFoundException("User not found"));
				String customerStriprId = user.getStripeId();
				user.getOrders().add(cart);
  
				PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
						.setCustomer(customerStriprId)
						.setCurrency(postBody.getCurrency())
						.setAmount(amount)
						.build();
				
				// Create a PaymentIntent with the order amount and currency
				PaymentIntent intent = PaymentIntent.create(createParams);
				
				// Send publishable key and PaymentIntent  details to client
				return gson.toJson(new CreatePaymentResponse("pk_test_XXXXXXXXXXXXXXXXXXX",
						intent.getClientSecret()));
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
	}		

	@PostMapping("/webhook")
	public String testWebhook(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
			
	    StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
		  buffer.append(line);
	    }
		String payload = buffer.toString();	
		String sigHeader = request.getHeader("Stripe-Signature");
		String endpointSecret = "whsec_XXXXXXXXXXXXXXXXXXXXXX";
		Event event = null;		
		try {
			event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
		} catch (SignatureVerificationException e) {
			// Invalid signatre
		    response.setStatus(400);
			return "";
		}
		
		switch (event.getType()) {
		case "payment_intent.succeeded":
			break;
		case "payment_intent.payment_failed":
			break;
		default:
			response.setStatus(400);
			return "";
		}
		
		response.setStatus(200);
		return "";
	}
		
	
	@PostMapping("/create-customer")
	public void createCustomer(HttpServletResponse response, @RequestBody CartDto cartDto) 
			throws Throwable {

		Stripe.apiKey = "sk_test_XXXXXXXXXXXXXXXXXXXX";
		
		Optional<User> userOpt = userRepository.findById(cartDto.getUser());
		Optional<Cart> cartOpt = cartRepository.findById(cartDto.getId());
		Cart cart = cartOpt.get();
		cart.setUser(cartDto.getUser());
		cartRepository.save(cart);
		
		if(!userOpt.isPresent()) {
			CustomerCreateParams params =
			  CustomerCreateParams.builder()
			    .setEmail(cartDto.getUser())
			    .build();
			Customer customer = Customer.create(params);
		    String customerId = customer.getId();
		    User user = new User();
			user.setId(cartDto.getUser());
			user.setStripeId(customerId);
			userRepository.save(user);
		}
	}
}
