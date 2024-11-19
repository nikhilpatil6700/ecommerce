package com.ShoppingWebApp.Contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.model.Cart;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.cart.CartService;
import com.ShoppingWebApp.service.cartItem.CartItemService;
import com.ShoppingWebApp.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/itemcart")
public class ItemCartController {
	
	private final CartItemService cartItemService;
	private final CartService cartService;
	private final UserService userService;
	
	//add new tem to cart
	@PostMapping("/addtocart")
	public ResponseEntity<ApiResponse> addItemToCart( @RequestParam Long productId,
													 @RequestParam Integer quantity){
		
		try { 
				User user = userService.getAuthenticatedUser();
				if (user == null) { // If user is not authenticated, handle gracefully
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                    .body(new ApiResponse("Unauthorized", "You may login and try again!"));
				}
				Cart cart = cartService.initializeNewCart(user);
			
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("item add success", null));
			
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("fail to add", null));
		}
		
	}
	
	//delete item from cart
	@DeleteMapping("/remove/{cartId}/{productId}")
	public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Long cartId
														,@PathVariable Long productId){
		
		try {
			cartItemService.removeItemFromCart(cartId, productId);
			return ResponseEntity.ok(new ApiResponse("remove success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
	}
	
	//update cart
	@PutMapping("/updatecart/{cartId}/{productId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId
														 ,@PathVariable Long productId
														 ,@RequestParam int quantity){
		try {
			cartItemService.updateItemQuantity(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("update item success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}
	}
	
	
	
	
	
	
	

}
