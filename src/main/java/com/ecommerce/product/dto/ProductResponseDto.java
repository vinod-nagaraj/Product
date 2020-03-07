package com.ecommerce.product.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
	private List<ProductList> productList;
	private Integer statusCode;
	private String message;
}
