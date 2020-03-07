package com.ecommerce.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	
	private Integer statusCode;
	private String message;
	private Long customerId;
	private String firstName;
	private Long cardNumber;

}
