package com.ShoppingWebApp.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {
	private Long cartItemId;
	private BigDecimal unitPrice;
	private int quantity;
	private ProductDto productDto;
	
}
