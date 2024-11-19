package com.ShoppingWebApp.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {

	private Long id;
	private Long productId;
	private String brand;
	private String productName;
	private int quntity;
	private BigDecimal price;

}
