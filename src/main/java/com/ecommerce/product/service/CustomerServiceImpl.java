package com.ecommerce.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.LoginRequestDto;
import com.ecommerce.product.dto.LoginResponseDto;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.exception.CustomerNotExistException;
import com.ecommerce.product.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hema J
 *  This class is used to perform all the customer related
 *              authentication operations
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepository;

	/**
	 * @author Hema J
	 * This method is used to verify the customer by getting email and password
	 * @param loginRequestDto which contains customer email and password 
	 * @return LoginResponseDto which gives the success or failure message
	 * @throws CustomerNotExistException 
	 */
	@Override
	public LoginResponseDto customerLogin(LoginRequestDto loginRequestDto) throws CustomerNotExistException {
		log.info("Entering into customerLogin method of CustomerServiceImpl");
		Optional<Customer> customer=customerRepository.findByCustomerEmailAndPassword(loginRequestDto.getCustomerEmail(),loginRequestDto.getPassword());
		LoginResponseDto loginResponseDto=new LoginResponseDto();
		if(!customer.isPresent()) {
			log.error("Exception occured in customerLogin method of CustomerServiceImpl");
			throw new CustomerNotExistException(ApplicationConstants.CUSTOMER_NOT_EXIST);
		} else {
			log.info("Login Successfull");
			loginResponseDto.setCustomerId(customer.get().getCustomerId());
			loginResponseDto.setFirstName(customer.get().getFirstName());
		}
		return loginResponseDto;
	}

}
