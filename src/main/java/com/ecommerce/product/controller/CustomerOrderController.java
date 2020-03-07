package com.ecommerce.product.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.OrderResponseDto;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.exception.OrderNotFoundException;
import com.ecommerce.product.service.CustomerOrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hema J
 *  This class is used to perform all the customer order related
 *               operations
 */


@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customerorders")
@Slf4j
public class CustomerOrderController {
	
	@Autowired
	CustomerOrderService customerOrderService;
	
	/**
	 * @author Hema J
	 * This method is used to get the list of orders on particular customerId
	 * @param customerId is a datatype of Long  
	 * @return OrderResponseDto which lists the orders on that particular customerId
	 * @throws OrderNotFoundException 
	 * @throws CustomerNotExistException 
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<OrderResponseDto> getOrderList(@PathVariable("customerId") Long customerId) throws CustomerNotExistException, OrderNotFoundException{
		log.info("Entering into getOrderList");
		OrderResponseDto orderResponseDto=customerOrderService.getOrderList(customerId);
		if(Objects.isNull(orderResponseDto)) {
			log.info("Order not found");
			OrderResponseDto orderResponse=new OrderResponseDto();
			orderResponse.setStatusCode(ApplicationConstants.ORDER_FAILURE_STATUS);
			orderResponse.setMessage(ApplicationConstants.ORDER_NOT_FOUND);
			return new ResponseEntity<>(orderResponse, HttpStatus.NOT_FOUND);
		} else {
			log.info("Orders on this customerId");
			orderResponseDto.setStatusCode(ApplicationConstants.ORDER_SUCCESS_STATUS);
			orderResponseDto.setMessage(ApplicationConstants.ORDER_FOUND);
			return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
		}
	}

}
