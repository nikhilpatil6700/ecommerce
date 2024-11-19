package com.ShoppingWebApp.request;

import java.math.BigDecimal;

import com.ShoppingWebApp.model.Category;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class ProductUpdateRequest {

	private long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
	

}
