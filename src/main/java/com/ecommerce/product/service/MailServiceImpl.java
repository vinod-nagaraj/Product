package com.ecommerce.product.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.OTPRequestDto;
import com.ecommerce.product.dto.OTPResponseDto;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.repository.CustomerRepository;
import com.ecommerce.product.util.SendMail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
	@Autowired
	SendMail sendMail;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void sendEmail(String emailId, String message, String subject) {
		log.info(":: Enter into LoginController--------::login()");
		sendMail.SendMailToCustomer(emailId, subject, message);
	}

	/**
	 * This method is used to sendOtp to the customer email
	 * 
	 * @author Chethana M 
	 * @param otpRequestDto takes customerId of type Long
	 * @return OTPResponseDto success/failure status
	 * @throws CustomerNotFoundException thrown when the customer details are
	 *                                   invalid
	 */
	@Override
	public OTPResponseDto sendOtp(OTPRequestDto otpRequestDto) throws CustomerNotFoundException {
		log.info(":: Enter into LoginController--------::login()");
		Random rand = new Random();
		String otp = Integer.toString(rand.nextInt(9999));
		Optional<Customer> customerResponse = customerRepository.findByCustomerId(otpRequestDto.getCustomerId());
		if (!customerResponse.isPresent()) {
			log.error("Exception Occured in sendOtp() metho of MailServiceImpl");
			throw new CustomerNotFoundException("Invalid Customer");
		}
		customerResponse.get().setOtp(Long.parseLong(otp));
		customerRepository.save(customerResponse.get());
		sendEmail(customerResponse.get().getCustomerEmail(), otp, "OTP");
		OTPResponseDto otpResponseDto = new OTPResponseDto();
		return otpResponseDto;
	}
}
