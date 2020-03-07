package com.ecommerce.product.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.LoginRequestDto;
import com.ecommerce.product.dto.LoginResponseDto;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hema J
 *  This class is used to perform all the customer related
 *              authentication operations
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	/**
	 * @author Hema J
	 * This method is used to verify the customer by getting email and password
	 * @param loginRequestDto which contains customer email and password 
	 * @return LoginResponseDto which gives the success or failure message
	 * @throws CustomerNotExistException 
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> customerLogin(@Valid LoginRequestDto loginRequestDto) throws CustomerNotExistException{
		log.info("Entering into login method of CustomerController");
		LoginResponseDto loginResponseDto=customerService.customerLogin(loginRequestDto);
		if (Objects.isNull(loginResponseDto)) {
			log.debug("Customer not found");
			LoginResponseDto loginResponse=new LoginResponseDto();
			loginResponse.setStatusCode(ApplicationConstants.LOGIN_FAILURE_STATUS);
			loginResponse.setMessage(ApplicationConstants.LOGIN_FAILURE);
			return new ResponseEntity<>(loginResponse, HttpStatus.NOT_FOUND);
		}else {
			log.info("Login Successfull");
			loginResponseDto.setStatusCode(ApplicationConstants.LOGIN_SUCCESS_STATUS);
			loginResponseDto.setMessage(ApplicationConstants.LOGIN_SUCCESS);
			return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
		}
	}

}
