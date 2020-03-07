package com.ecommerce.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
	
	private Integer statusCode;
	private String message;
	
	private List<OrderListDto> orderListDto;

}
