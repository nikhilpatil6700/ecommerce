package com.ShoppingWebApp.ExceptionHandler;


public class CategoryAlreadyExistException extends RuntimeException {

	public CategoryAlreadyExistException(String message) {
		super(message);
	}
}
