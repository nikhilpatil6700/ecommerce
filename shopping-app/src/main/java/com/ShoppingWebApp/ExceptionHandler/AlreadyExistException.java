package com.ShoppingWebApp.ExceptionHandler;

public class AlreadyExistException extends RuntimeException {
 
	public AlreadyExistException(String message) {
		super(message);
	}
}
