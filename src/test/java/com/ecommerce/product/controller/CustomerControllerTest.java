
package com.ecommerce.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.ecommerce.product.dto.LoginRequestDto;
import com.ecommerce.product.dto.LoginResponseDto;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.service.CustomerServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerServiceImpl customerServiceImpl;

	static LoginResponseDto loginResponseDto = new LoginResponseDto();
	static LoginRequestDto loginRequestDto = new LoginRequestDto();

	@Before
	public void setUp() {
		loginRequestDto.setCustomerEmail("hema");
		loginRequestDto.setPassword("hema");
		loginResponseDto.setCustomerId(89L);
	}

	@Test
	public void testCustomerLoginPositive() throws CustomerNotExistException {
		log.info("Customer Login Positive");
		Mockito.when(customerServiceImpl.customerLogin(loginRequestDto)).thenReturn(loginResponseDto);
		Integer result = customerController.customerLogin(loginRequestDto).getStatusCodeValue();
		assertEquals(200, result);
	}

	@Test
	public void testCustomerLoginNegative() throws CustomerNotExistException {
		log.info("Customer Login Negative");
		Mockito.when(customerServiceImpl.customerLogin(loginRequestDto)).thenReturn(null);
		ResponseEntity<LoginResponseDto> loginResponse = customerController.customerLogin(loginRequestDto);
		Assert.assertNotNull(loginResponse);
	}

}
