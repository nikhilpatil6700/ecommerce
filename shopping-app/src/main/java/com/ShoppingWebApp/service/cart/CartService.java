package com.ShoppingWebApp.service.cart;

import java.math.BigDecimal;

import com.ShoppingWebApp.dto.CartDto;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.model.User;

public interface CartService {

	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart initializeNewCart(User user);
	public CartDto convertToDto(Cart cart);
}
