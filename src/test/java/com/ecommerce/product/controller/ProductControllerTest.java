package com.ecommerce.product.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.BuyRequestDto;
import com.ecommerce.product.dto.BuyResponseDto;
import com.ecommerce.product.dto.ProductList;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.exception.OtpInvalidException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductQuantityInvalidException;
import com.ecommerce.product.exception.PurchaseCannotProceedException;
import com.ecommerce.product.service.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductControllerTest {
	@Mock
	ProductService productService;

	@InjectMocks
	ProductController productController;

	List<ProductList> productList = null;
	List<ProductList> productList1 = null;
	ProductList productDetailsList = null;
	BuyResponseDto buyResponseDto = null;
	BuyRequestDto buyRequestDto = null;
	String successMessage;
	String failureMessage;

	@Before
	public void before() {
		productList = new ArrayList<>();
		productDetailsList = new ProductList();
		productDetailsList.setProductName("abc");
		productList.add(productDetailsList);

		buyResponseDto = new BuyResponseDto();
		buyRequestDto = new BuyRequestDto();
		buyRequestDto.setCustomerId(100L);
		buyResponseDto.setStatusCode(ApplicationConstants.PRODUCT_SUCCESSCODE);

		successMessage = ApplicationConstants.BUY_SUCCESSMESSAGE;
		failureMessage=ApplicationConstants.BUY_FAILUREMESSAGE;
	}

	@Test
	public void testGetProductDetailsPositive() {
		Mockito.when(productService.getProductDetails("abc")).thenReturn(productList);
		Integer expected = productController.getProductDetails("abc").getStatusCodeValue();
		assertEquals(ApplicationConstants.PRODUCT_SUCCESSCODE, expected);
	}

	@Test
	public void testGetProductDetailsNegative() {
		Mockito.when(productService.getProductDetails("def")).thenReturn(productList1);
		Integer expected = productController.getProductDetails("abc").getStatusCodeValue();
		assertEquals(ApplicationConstants.PRODUCT_FAILURECODE, expected);
	}

	@Test
	public void testBuyProductPositive() throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException,OtpInvalidException  {
		Mockito.when(productService.buyProduct(buyRequestDto)).thenReturn(successMessage);
		Integer expected = productController.buyProduct(buyRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.PRODUCT_SUCCESSCODE, expected);
	}
	
	@Test
	public void testBuyProductNegative() throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException,OtpInvalidException  {
		Mockito.when(productService.buyProduct(buyRequestDto)).thenReturn(failureMessage);
		Integer expected = productController.buyProduct(buyRequestDto).getStatusCodeValue();
		assertEquals(ApplicationConstants.PRODUCT_FAILURECODE, expected);
	}

}
