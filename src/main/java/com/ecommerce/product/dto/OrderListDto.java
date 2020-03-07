package com.ecommerce.product.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListDto {
	
	private Long orderId;
	private String productName;
	private LocalDateTime purchasedTime;

}
