package com.ecommerce.product.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.product.dto.BuyRequestDto;
import com.ecommerce.product.dto.ProductList;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.exception.OtpInvalidException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductQuantityInvalidException;
import com.ecommerce.product.exception.PurchaseCannotProceedException;
import com.ecommerce.product.repository.CustomerRepository;
import com.ecommerce.product.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductServiceTest {
	@InjectMocks
	ProductServiceImpl productServiceImpl;

	@Mock
	ProductRepository productRepository;

	@Mock
	CustomerRepository customerRepository;

	List<Product> productList = null;
	Product productDetailsList = null;

	@Before
	public void before() {
		productList = new ArrayList<>();
		productDetailsList = new Product();
		productDetailsList.setProductName("abc");
		productList.add(productDetailsList);
	}

	@Test
	public void testGetProductDetailsPositive() {
		Mockito.when(productRepository.findByProductNameContaining("abc")).thenReturn(productList);
		List<ProductList> expected = productServiceImpl.getProductDetails("abc");
		assertEquals(productList.size(), expected.size());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void testBuyProductCustomerNotPresent() throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException,OtpInvalidException  {
		BuyRequestDto buyRequestDto = new BuyRequestDto();
		Customer customer = new Customer();
		customer.setCustomerId(2L);
		buyRequestDto.setCustomerId(1L);
		Mockito.when(customerRepository.findByCustomerId(2L)).thenReturn(Optional.of(customer));
		productServiceImpl.buyProduct(buyRequestDto);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testBuyProductNotPresent() throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException,OtpInvalidException  {
		BuyRequestDto buyRequestDto = new BuyRequestDto();
		Customer customer = new Customer();
		customer.setCustomerId(2L);
		Product product = new Product();
		product.setProductId(1L);
		buyRequestDto.setCustomerId(2L);
		buyRequestDto.setProductId(2L);
		Mockito.when(customerRepository.findByCustomerId(2L)).thenReturn(Optional.of(customer));
		Mockito.when(productRepository.findByProductId(1L)).thenReturn(Optional.of(product));
		productServiceImpl.buyProduct(buyRequestDto);
	}

	@Test(expected = ProductQuantityInvalidException.class)
	public void testBuyProductAvailableException() throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException ,OtpInvalidException {
		BuyRequestDto buyRequestDto = new BuyRequestDto();
		Customer customer = new Customer();
		customer.setCustomerId(2L);
		Product product = new Product();
		product.setProductId(2L);
		product.setProductQuantity(2);
		buyRequestDto.setCustomerId(2L);
		buyRequestDto.setProductId(2L);
		buyRequestDto.setProductQuantity(3);
		Mockito.when(customerRepository.findByCustomerId(2L)).thenReturn(Optional.of(customer));
		Mockito.when(productRepository.findByProductId(2L)).thenReturn(Optional.of(product));
		productServiceImpl.buyProduct(buyRequestDto);
	}
}
