package com.ShoppingWebApp.dto;

import java.util.List;

import com.ShoppingWebApp.model.Category;
import com.ShoppingWebApp.model.Image;

import lombok.Data;


@Data
public class ProductDto {
	
	private long id;
	private String name;
	private String brand;
	private double price;
	private int inventory;
	private String description;
	private List<ImageDto> image;
	private Category category;

}
