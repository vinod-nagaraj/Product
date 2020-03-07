package com.ecommerce.product.service;

import com.ecommerce.product.dto.OrderResponseDto;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.exception.OrderNotFoundException;

public interface CustomerOrderService {

	OrderResponseDto getOrderList(Long customerId) throws CustomerNotExistException, OrderNotFoundException;

}
