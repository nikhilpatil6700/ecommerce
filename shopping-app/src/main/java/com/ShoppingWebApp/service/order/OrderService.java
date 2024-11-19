package com.ShoppingWebApp.service.order;

import java.util.List;

import com.ShoppingWebApp.dto.OrderDto;
import com.ShoppingWebApp.model.Order;

public interface OrderService {

	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrder(Long userId);
	public OrderDto convertToDto(Order order);
	List<OrderDto> getUserOrderByEmail(String email);
}
