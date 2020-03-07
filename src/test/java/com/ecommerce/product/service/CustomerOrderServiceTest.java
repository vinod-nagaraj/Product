package com.ecommerce.product.service;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.OrderListDto;
import com.ecommerce.product.dto.OrderResponseDto;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.entity.CustomerOrder;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.exception.OrderNotFoundException;
import com.ecommerce.product.repository.CustomerOrderRepository;
import com.ecommerce.product.repository.CustomerRepository;
import com.ecommerce.product.repository.ProductRepository;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerOrderServiceTest {

	@InjectMocks
	CustomerOrderServiceImpl customerOrderServiceImpl;

	@Mock
	CustomerOrderRepository customerOrderRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	CustomerRepository customerRepository;

	 Customer customer = new Customer();
	 CustomerOrder customerOrder = new CustomerOrder();
	 List<CustomerOrder> customerOrderList = new ArrayList<>();
	 List<OrderListDto> orderListDto = new ArrayList<>();
	 OrderListDto orderList = new OrderListDto();
	 Product product = new Product();
	 OrderResponseDto orderResponseDto = new OrderResponseDto();

	@Before
	public void setUp() {
		customer.setCustomerId(1L);
		orderList.setOrderId(1L);
		orderListDto.add(orderList);
		product.setProductId(1L);
		product.setProductName("a");
		customerOrder.setCustomerId(customer);
		customerOrder.setPurchasedTime(LocalDateTime.now());
		customerOrder.setProductId(product);
		customerOrderList.add(customerOrder);
		orderResponseDto.setMessage(ApplicationConstants.ORDER_FOUND);
	}

	@Test
	public void testGetOrderListPositive() throws CustomerNotExistException, OrderNotFoundException {
		log.info("Entering into testGetOrderListPositive");
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
		Mockito.when(customerOrderRepository.findByCustomerId(customer)).thenReturn(customerOrderList);
		Mockito.when(productRepository.findByProductId(1L)).thenReturn(Optional.of(product));
		OrderResponseDto orderResponseDto=customerOrderServiceImpl.getOrderList(1L);
		Assert.assertNotNull(orderResponseDto);
	}

	@Test(expected = CustomerNotExistException.class)
	public void testGetOrderListCustomerNull() throws CustomerNotExistException, OrderNotFoundException {
		log.info("Entering into testGetOrderListCustomerNull");
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.ofNullable(null));
		customerOrderServiceImpl.getOrderList(1L);
	}

	@Test(expected = OrderNotFoundException.class)
	public void testGetOrderListOrderNull() throws CustomerNotExistException, OrderNotFoundException {
		log.info("Entering into testGetOrderListOrderNull");
		List<CustomerOrder> customerOrderLists = new ArrayList<>();
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
		Mockito.when(customerOrderRepository.findByCustomerId(customer)).thenReturn(customerOrderLists);
		customerOrderServiceImpl.getOrderList(1L);
	}
}
