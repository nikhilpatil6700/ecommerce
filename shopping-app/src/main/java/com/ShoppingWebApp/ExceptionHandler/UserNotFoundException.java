package com.ShoppingWebApp.ExceptionHandler;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message) {
		super(message);
	}
}
