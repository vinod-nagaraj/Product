package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.dto.BuyRequestDto;
import com.ecommerce.product.dto.ProductList;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.exception.OtpInvalidException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductQuantityInvalidException;
import com.ecommerce.product.exception.PurchaseCannotProceedException;

public interface ProductService {

	List<ProductList> getProductDetails(String productName);

	String buyProduct(BuyRequestDto buyRequestDto) throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException, OtpInvalidException;

}
