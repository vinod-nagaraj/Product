package com.ecommerce.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class PurchaseCannotProceedException extends Exception {
	private static final long serialVersionUID = 1L;

	public PurchaseCannotProceedException(String exception) {
		super(exception);
	}
}


	
	
