package com.patrykmaryn.shopping.cart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	      .cors()
          .and()
	       .authorizeRequests().antMatchers("css/**", "js**", "/assets/**",
	    		   "/**/*.css", "/**/*.js", "/resources/**",
	    		   "/resources/**", "/static/**", "/css/**", "/js/**", "/resources/static/**",
	    		   "/create-payment-intent", 
	    		   "/create-customer",
	    		   "/script.js**", "/script.js/**",
	    		   "/script.js**", "https://js.stripe.com/**", "/webhook", "/webhook/**",
	    		   "https://ng-maryn-shopping-cart.herokuapp.com/**",
	    		   "http://localhost:4200/**",
	    		   "/api/**",
	    		   "/webjars/**", "/error**", "/stripe**").permitAll()
		  .and()
	  	   .authorizeRequests()
	       .anyRequest().authenticated()

	    .and()
	    .csrf().disable();
		
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		  CorsConfiguration config = new CorsConfiguration();
		  config.setAllowedOrigins(ImmutableList.of("http://localhost:4200" 
				  //"https://chat-maryn-demo.herokuapp.com"
				  ));
		  config.setAllowCredentials(true);
		  config.setAllowedMethods(ImmutableList.of("HEAD",
	                "GET", "POST", "PUT", "DELETE", "PATCH"));
		  config.setAllowedHeaders(ImmutableList.of("Authorization", 
				  "Cache-Control", "Content-Type"));
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", config);
	      return source;
		}
	
	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//		  CorsConfiguration config = new CorsConfiguration();
//		  config.setAllowedOrigins(ImmutableList.of("http://localhost:4200" 
//				  //"https://chat-maryn-demo.herokuapp.com"
//				  ));
//		  config.setAllowCredentials(true);
//		  config.setAllowedMethods(ImmutableList.of("HEAD",
//	                "GET", "POST", "PUT", "DELETE", "PATCH"));
//		  config.setAllowedHeaders(ImmutableList.of("Authorization", 
//				  "Cache-Control", "Content-Type"));
//	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	      source.registerCorsConfiguration("/**", config);
//	      return source;
//		}	
}




