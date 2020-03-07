package com.ecommerce.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class ProductQuantityInvalidException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProductQuantityInvalidException(String exception) {
		super(exception);
	}
}


	
	
