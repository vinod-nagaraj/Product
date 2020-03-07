package com.ecommerce.product.service;

import com.ecommerce.product.dto.LoginRequestDto;
import com.ecommerce.product.dto.LoginResponseDto;
import com.ecommerce.product.exception.CustomerNotExistException;

public interface CustomerService {

	LoginResponseDto customerLogin(LoginRequestDto loginRequestDto) throws CustomerNotExistException;


}
