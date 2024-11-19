package com.ShoppingWebApp.service.cartItem;

import com.ShoppingWebApp.dto.CartItemDto;
import com.ShoppingWebApp.model.CartItem;

public interface CartItemService {

	void addItemToCart(Long cartId,Long productId,int quantity);
	void removeItemFromCart(Long cartId,Long productId);
	void updateItemQuantity(Long cartId,Long productId,int quantity);
	public CartItemDto convertToCartItemDto(CartItem cartItem);

}
