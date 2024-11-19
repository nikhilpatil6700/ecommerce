package com.ShoppingWebApp.Contollers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.dto.CartDto;
import com.ShoppingWebApp.dto.CartItemDto;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.cart.CartService;
import com.ShoppingWebApp.service.cartItem.CartItemService;
import com.ShoppingWebApp.service.product.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final CartItemService cartItemService;
	private final ProductService productService;
	
	@GetMapping("/my-cart/{id}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long id){
		try {
			Cart cart = cartService.getCart(id);
			CartDto cartDto = cartService.convertToDto(cart);
			return ResponseEntity.ok(new ApiResponse("success", cartDto));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
		
	@DeleteMapping("/delete-cart/{id}") 
	public ResponseEntity<ApiResponse> deleteCart(@PathVariable Long id){
		try {
			cartService.clearCart(id);
			return  ResponseEntity.ok(new ApiResponse("cart delete", null));
		
		} catch (ResourceNotFoundException e) {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
		}
	}
	
	
	
	
	@GetMapping("/total-price/{id}")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id){
		try {
			BigDecimal totalPrice = cartService.getTotalPrice(id);
			return ResponseEntity.ok(new ApiResponse("total price", totalPrice));

		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
		}
		
	}
	
	
	
	
	
	
}
