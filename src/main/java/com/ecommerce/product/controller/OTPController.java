package com.ecommerce.product.controller;

/**
 * @author Chethana
 * This Class performs the operations related to otp service
 **/
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.OTPRequestDto;
import com.ecommerce.product.dto.OTPResponseDto;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.service.MailService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/otp")
@Slf4j
public class OTPController {

	@Autowired
	MailService mailService;

	/**
	 * This method is used to generate otp and send to the logged in customer Email
	 * 
	 * @author Chethana
	 * @param otpRequestDto takes customerId of type Long
	 * @return OTPResponseDto success/failure status
	 * @throws CustomerNotFoundException thrown when the customer details are
	 *                                   invalid
	 */
	@PostMapping
	public ResponseEntity<OTPResponseDto> OTPResponse(@Valid @RequestBody OTPRequestDto otpRequestDto)
			throws CustomerNotFoundException {
		log.info("Entering into OTPResponse() of OTPController");
		OTPResponseDto otpResponseDto = mailService.sendOtp(otpRequestDto);
		if (Objects.isNull(otpResponseDto)) {
			OTPResponseDto otpResponse = new OTPResponseDto();
			otpResponse.setMessage(ApplicationConstants.FAILURE);
			otpResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(otpResponseDto, HttpStatus.NOT_FOUND);
		}
		otpResponseDto.setMessage(ApplicationConstants.SUCCESS);
		otpResponseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(otpResponseDto, HttpStatus.OK);
	}

}
