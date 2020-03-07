package com.ecommerce.product.exception;

public class CustomerNotExistException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public CustomerNotExistException(String exception) {
		super(exception);
	}

}
