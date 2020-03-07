package com.ecommerce.product.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "productSequence", initialValue = 121, allocationSize = 1)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
	private Long productId;
	private String productName;
	private String productDescription;
	private Double productPrice;
	private String productCategory;
	private Integer productQuantity;
}
