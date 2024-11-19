package com.ShoppingWebApp.ExceptionHandler;


public class CategoryNotFoundException extends RuntimeException{

	public CategoryNotFoundException(String name) {
		super(name);
	}
}
