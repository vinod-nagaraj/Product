package com.ecommerce.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductList {
	private Long productId;
	private String productName;
	private Double productPrice;
	private String productDescription;
	private String productCategory;
	private Integer productQuantity;
}
