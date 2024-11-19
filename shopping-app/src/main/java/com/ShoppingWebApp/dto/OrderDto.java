package com.ShoppingWebApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.ShoppingWebApp.model.OrderItem;
import com.ShoppingWebApp.model.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {

	private Long orderId;
	private Long userId;
	private BigDecimal totalAmount;
	private LocalDate orderDate;
	private OrderStatus orderStatus;
	private Set<OrderItemDto> orderItem=new HashSet<>();
	
}
