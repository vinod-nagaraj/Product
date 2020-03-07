package com.ecommerce.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyRequestDto {
	private Long customerId;
	private Long productId;
	private Integer productQuantity;
	private Long cardNumber;
	private Integer cvv;
	private Long otp;
}
