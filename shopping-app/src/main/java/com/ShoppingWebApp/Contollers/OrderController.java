 package com.ShoppingWebApp.Contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ShoppingWebApp.ExceptionHandler.ResourceNotFoundException;
import com.ShoppingWebApp.dto.OrderDto;
import com.ShoppingWebApp.model.Order;
import com.ShoppingWebApp.model.User;
import com.ShoppingWebApp.response.ApiResponse;
import com.ShoppingWebApp.service.order.OrderService;
import com.ShoppingWebApp.service.user.UserService;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;
	
	//get order by order id
	@GetMapping("/order/{orderId}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
		try {
			OrderDto order = orderService.getOrder(orderId);
			return ResponseEntity.ok(new ApiResponse("your order", order));
			
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
		}
		
	}
	
	
	@PostMapping("/order")
	public ResponseEntity<ApiResponse> createOrder (@RequestParam Long userId){
		try {
			Order placeOrder = orderService.placeOrder(userId);
			OrderDto orderDto = orderService.convertToDto(placeOrder);
			return ResponseEntity.ok(new ApiResponse("order status", orderDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("not order from ", userId+" id"));
		}
	}
	
	
	//get all order of user by userId
	@GetMapping("/order/user")
	public ResponseEntity<ApiResponse> getAllUserOrder() {
		
		User user = userService.getAuthenticatedUser();
		try {
			if(user==null ) {
				
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new ApiResponse("Unauthorized", "You may login and try again!"));
				}
			 List<OrderDto> userOrder = orderService.getUserOrderByEmail(user.getEmail());
			 	return ResponseEntity.ok(new ApiResponse("order status", userOrder));
		} catch (ResourceNotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not order from ", user.getEmail()));
		}	
		
	}
	
	
	


}
