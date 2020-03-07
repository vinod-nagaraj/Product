package com.ecommerce.product.service;

import com.ecommerce.product.dto.OTPRequestDto;
import com.ecommerce.product.dto.OTPResponseDto;
import com.ecommerce.product.exception.CustomerNotFoundException;

public interface MailService {

	void sendEmail(String emailId, String message, String subject);


	OTPResponseDto sendOtp(OTPRequestDto otpRequestDto) throws CustomerNotFoundException;

}
