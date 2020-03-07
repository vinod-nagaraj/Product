package com.ecommerce.product.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.OrderResponseDto;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.exception.OrderNotFoundException;
import com.ecommerce.product.service.CustomerOrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerOrderControllerTest {

	@InjectMocks
	CustomerOrderController customerOrderController;

	@Mock
	CustomerOrderServiceImpl customerOrderServiceImpl;

	static Customer customer = new Customer();
	static OrderResponseDto orderResponseDto = new OrderResponseDto();

	@Before
	public void setUp() {
		customer.setCustomerId(1L);
		orderResponseDto.setMessage(ApplicationConstants.ORDER_FOUND);
	}

	@Test
	public void testGetOrderListPositive() throws CustomerNotExistException, OrderNotFoundException {
		log.info("Inside testGetOrderListPositive");
		Mockito.when(customerOrderServiceImpl.getOrderList(1L)).thenReturn(orderResponseDto);
		Integer result = customerOrderController.getOrderList(1L).getStatusCodeValue();
		assertEquals(200, result);
	}

	@Test
	public void testGetOrderListNegative() throws CustomerNotExistException, OrderNotFoundException {
		log.info("Inside testGetOrderListNegative");
		Mockito.when(customerOrderServiceImpl.getOrderList(1L)).thenReturn(orderResponseDto);
		Integer result = customerOrderController.getOrderList(2L).getStatusCodeValue();
		assertEquals(404, result);
	}

}
