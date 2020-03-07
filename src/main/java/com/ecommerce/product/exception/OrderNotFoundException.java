package com.ecommerce.product.exception;

public class OrderNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String exception) {
		super(exception);
	}


}
