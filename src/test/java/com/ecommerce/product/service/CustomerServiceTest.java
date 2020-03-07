package com.ecommerce.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.LoginRequestDto;
import com.ecommerce.product.dto.LoginResponseDto;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceTest {
	
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	static LoginResponseDto loginResponseDto=new LoginResponseDto();
	static LoginRequestDto loginRequestDto=new LoginRequestDto();
	static Customer customer=new Customer();
	
	@Before
	public void setUp() {
		loginRequestDto.setCustomerEmail("hema");
		loginRequestDto.setPassword("hema");
		customer.setCustomerId(9L);
		loginResponseDto.setMessage(ApplicationConstants.LOGIN_SUCCESS);
	}
	
	@Test
	public void testCustomerLoginPositive() throws CustomerNotExistException {
		log.info("Entering into testCustomerLoginPositive");
		Mockito.when(customerRepository.findByCustomerEmailAndPassword("hema", "hema")).thenReturn(Optional.of(customer));
		customerServiceImpl.customerLogin(loginRequestDto);
		assertEquals(ApplicationConstants.LOGIN_SUCCESS, loginResponseDto.getMessage());
	}
	
	@Test(expected = CustomerNotExistException.class)
	public void testCustomerLoginNegative() throws CustomerNotExistException {
		log.info("Entering into testCustomerLoginNegative");
		Mockito.when(customerRepository.findByCustomerEmailAndPassword("hema1", "hema1")).thenReturn(Optional.of(customer));
		customerServiceImpl.customerLogin(loginRequestDto);
	}
	

}
