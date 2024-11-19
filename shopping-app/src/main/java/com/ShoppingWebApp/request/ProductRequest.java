package com.ShoppingWebApp.request;

import java.math.BigDecimal;
import java.util.List;

import com.ShoppingWebApp.model.Category;

import lombok.Data;


@Data
public class ProductRequest {
	private long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
	

}
