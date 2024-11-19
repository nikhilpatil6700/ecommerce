package com.ShoppingWebApp.service.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.Repository.CartItemRepository;
import com.ShoppingWebApp.Repository.CartRepository;
import com.ShoppingWebApp.dto.CartDto;
import com.ShoppingWebApp.dto.CartItemDto;
import com.ShoppingWebApp.dto.ProductDto;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.model.CartItem;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final ModelMapper mapper;
	private final ProductService productService;
	
	
	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		return cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long id) {
		
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getCartItems().clear(); //it just to clear in-memory data
		cartRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}

	
	public Cart initializeNewCart(User user) {
		return Optional.ofNullable(getUserCart(user.getUserId())).orElseGet(()->
		{
			Cart cart=new Cart();
			cart.setUser(user);
			return cartRepository.save(cart);
			
		});
		
	}
	
	public Cart getUserCart(Long userId) {
		return cartRepository.findByUser_userId(userId);
	}
	
	public CartDto convertToDto(Cart cart) {
		 CartDto cartDto = mapper.map(cart, CartDto.class);
		 
		   Set<CartItemDto> cartItems = cart.getCartItems().stream()
				   .map(cartItem->{
					   CartItemDto cartItemDto = mapper.map(cartItem, CartItemDto.class);
			 
					   ProductDto productDto = productService.convertToDto(cartItem.getProduct());
					   cartItemDto.setProductDto(productDto);
			  
			  return cartItemDto;

		  }).collect(Collectors.toSet());
		   
		  cartDto.setCartItem(cartItems);
		  
		  return cartDto;
		  
		  }
		 
		 
		 
		/* List<CartItemDto> cartItem = cartDto.getCartItem();
		 cartItem.stream().map(items->{
			 CartItemDto cartItemDto = mapper.map(items, CartItemDto.class);
			 productService.convertToDto(items.)
			 return cartItem;
		 }).toList();
		 
		 
		 cartDto.setCartItem(cartItem);*/

	
	
	
}
