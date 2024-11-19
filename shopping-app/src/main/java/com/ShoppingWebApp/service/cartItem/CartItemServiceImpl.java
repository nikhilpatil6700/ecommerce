package com.ShoppingWebApp.service.cartItem;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.Repository.CartItemRepository;
import com.ShoppingWebApp.Repository.CartRepository;
import com.ShoppingWebApp.dto.CartItemDto;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.model.CartItem;
import com.ShoppingWebApp.model.Product;
import com.ShoppingWebApp.service.cart.CartService;
import com.ShoppingWebApp.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartService cartService;
	private final ProductService productService;
	private final CartRepository cartRepository;
	private final ModelMapper mapper;
	
	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
		
		Cart cart = cartService.getCart(cartId);
		Product product = productService.getProductById(productId);
		
		   CartItem cartItem = cart.getCartItems().stream()
						   .filter(item->item.getProduct().getId().equals(productId))
						   .findFirst().orElse(new CartItem());
		
		   if(cartItem.getCartItemId()==null) {
			   cartItem.setCart(cart);
			   cartItem.setProduct(product);
			   cartItem.setQuantity(quantity);
			   cartItem.setUnitPrice(product.getPrice());
		   }
		   else {
			cartItem.setQuantity(quantity+cartItem.getQuantity());
		}
		
		cartItem.setTotalPrice();
		cart.addItem(cartItem);
		
		cartItemRepository.save(cartItem);
		cartRepository.save(cart);
		
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		Cart cart= cartService.getCart(cartId);
		
		CartItem cartItem = cart.getCartItems().stream()
						   .filter(item->item.getProduct().getId().equals(productId))
						   .findFirst()
						   .orElseThrow(()->new ResourceNotFoundException("product not found"));
		
		cart.removeItem(cartItem);
		cartItemRepository.save(cartItem);
		
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart =cartService.getCart(cartId);
		
		 cart.getCartItems().stream()
							.filter(item->item.getProduct().getId().equals(productId))
							.findFirst()
							.ifPresent(item->{
								item.setQuantity(quantity);
								item.setUnitPrice(item.getProduct().getPrice());
								item.setTotalPrice();
							});
		
		 BigDecimal totalAmount = cart.getCartItems().stream()
				 									 .map(CartItem::getTotalPrice)
				 									 .reduce(BigDecimal.ZERO, BigDecimal::add);
		 cart.setTotalAmount(totalAmount);
		 cartRepository.save(cart);
		
	}
	
	public CartItemDto convertToCartItemDto(CartItem cartItem) {
		return mapper.map(cartItem, CartItemDto.class);
	}

	
	
}
