package com.ecommerce.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

/**
 * @author Hema J
 *  This class is used to perform all the customer order related
 *               operations
 */



@Slf4j
@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	@Autowired
	CustomerOrderRepository customerOrderRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ProductRepository productRepository; 

	/**
	 * @author Hema J This method is used to get the list of orders on particular
	 *         customerId
	 * @param customerId is a datatype of Long
	 * @return OrderResponseDto which lists the orders on that particular customerId
	 * @throws CustomerNotExistException
	 * @throws OrderNotFoundException
	 */
	@Override
	public OrderResponseDto getOrderList(Long customerId) throws CustomerNotExistException, OrderNotFoundException {
		log.info("Entering into getOrderList");
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent()) {
			log.error("Customer not found");
			throw new CustomerNotExistException(ApplicationConstants.CUSTOMER_NOT_EXIST);
		}
		List<CustomerOrder> customerOrderList = customerOrderRepository.findByCustomerId(customer.get());
		if (customerOrderList.isEmpty()) {
			log.error("No orders found on this customerId");
			throw new OrderNotFoundException(ApplicationConstants.ORDER_NOT_FOUND);
		}
		List<OrderListDto> orderListDto = new ArrayList<>();
		customerOrderList.forEach(orders -> {
			OrderListDto orderList = new OrderListDto();

			Optional<Product> product = productRepository.findByProductId(orders.getProductId().getProductId());
			orderList.setOrderId(orders.getOrderId());
			orderList.setProductName(product.get().getProductName());
			orderList.setPurchasedTime(orders.getPurchasedTime());
			orderListDto.add(orderList);
		});
		log.info("Showing the customer order list");
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		orderResponseDto.setOrderListDto(orderListDto);
		return orderResponseDto;
	}

}
