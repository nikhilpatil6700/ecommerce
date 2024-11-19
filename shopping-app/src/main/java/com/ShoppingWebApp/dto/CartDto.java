package com.ShoppingWebApp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ShoppingWebApp.model.CartItem;

import lombok.Data;


public class CartDto {

	private Long id;
	private BigDecimal totalAmount;
	private Set<CartItemDto> cartItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Set<CartItemDto> getCartItem() {
		return cartItem;
	}
	public void setCartItem(Set<CartItemDto> cartItem) {
		this.cartItem = cartItem;
	}
	
	
	
}
