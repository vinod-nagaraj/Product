package com.ecommerce.product.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.BuyRequestDto;
import com.ecommerce.product.dto.ProductList;
import com.ecommerce.product.entity.Customer;
import com.ecommerce.product.entity.CustomerOrder;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.exception.OtpInvalidException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductQuantityInvalidException;
import com.ecommerce.product.exception.PurchaseCannotProceedException;
import com.ecommerce.product.repository.CustomerOrderRepository;
import com.ecommerce.product.repository.CustomerRepository;
import com.ecommerce.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
/*
 * Used for displaying product and buying a product
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerOrderRepository customerOrderRepository;

	/**
	 * @author Muthu 
	 * 
	 * Method is used to display the product details based on the
	 *         partial name entered
	 * @param productName
	 * @return ProductResponseDto which contains details of product that includes
	 *         its name,description,quantity available,category and its price
	 */
	@Override
	public List<ProductList> getProductDetails(String productName) {
		log.info("fetching details from repository");
		List<ProductList> productList = new ArrayList<>();
		List<Product> product = productRepository.findByProductNameContaining(productName);
		product.forEach(productDetails -> {
			ProductList productDetailsList = new ProductList();
			BeanUtils.copyProperties(productDetails, productDetailsList);
			productList.add(productDetailsList);
		});
		return productList;
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used to buy a product and save the transactions using rest
	 *         template
	 * 
	 * @param buyRequestDto which contains userId,productId,productQuantity,CVV and
	 *                      cardNumber
	 * @return BuyResponseDto contains status code and message
	 * @throws CustomerNotFoundException
	 * @throws ProductNotFoundException
	 * @throws ProductQuantityInvalidException
	 * @throws PurchaseCannotProceedException
	 * @throws OtpInvalidException 
	 */

	@Override
	public String buyProduct(BuyRequestDto buyRequestDto) throws CustomerNotFoundException, ProductNotFoundException,
			ProductQuantityInvalidException, PurchaseCannotProceedException, OtpInvalidException {
		Long customerId = buyRequestDto.getCustomerId();
		Long productId = buyRequestDto.getProductId();
		Integer productQuantity = buyRequestDto.getProductQuantity();
		CustomerOrder customerOrder = new CustomerOrder();
		Optional<Customer> customerDetails = customerRepository.findByCustomerId(customerId);
		if (!(customerDetails.isPresent())) {
			log.error(ApplicationConstants.CUSTOMERNOTFOUND_MESSAGE);
			throw new CustomerNotFoundException(ApplicationConstants.CUSTOMERNOTFOUND_MESSAGE);
		}
		Optional<Product> productDetails = productRepository.findByProductId(productId);
		if (!(productDetails.isPresent())) {
			log.error(ApplicationConstants.PRODUCTNOTFOUND_MESSAGE);
			throw new ProductNotFoundException(ApplicationConstants.PRODUCTNOTFOUND_MESSAGE);
		}
		Integer productAvailableQuantity = productDetails.get().getProductQuantity();
		if (productAvailableQuantity <= productQuantity) {
			log.error(ApplicationConstants.PRODUCTQUANTITYINVALID_MESSAGE);
			throw new ProductQuantityInvalidException(ApplicationConstants.PRODUCTQUANTITYINVALID_MESSAGE);
		}
		Long cardNumber = buyRequestDto.getCardNumber();
		Integer cvv = buyRequestDto.getCvv();
		Double price = productQuantity * productDetails.get().getProductPrice();

		final String uri = "http://10.117.189.62:8087/bank/cards?cardNumber=" + cardNumber + "&cvv=" + cvv + "&price="
				+ price;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		Boolean response = restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class).getBody();
		if (response.equals(ApplicationConstants.FALSE)) {
			log.error(ApplicationConstants.PURCHASE_CANNOTMESSAGE);
			throw new PurchaseCannotProceedException(ApplicationConstants.PURCHASE_CANNOTMESSAGE);
		}

		final String uri1 = "http://10.117.189.62:8087/bank/transactions?cardNumber=" + cardNumber + "&price=" + price;
		RestTemplate restTemplate1 = new RestTemplate();
		HttpHeaders headers1 = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity1 = new HttpEntity<>("parameters", headers1);
		Boolean response1 = restTemplate1.exchange(uri1, HttpMethod.POST, entity1, Boolean.class).getBody();
		if (response1.equals(ApplicationConstants.FALSE)) {
			log.error(ApplicationConstants.PURCHASE_CANNOTMESSAGE);
			throw new PurchaseCannotProceedException(ApplicationConstants.PURCHASE_CANNOTMESSAGE);
		}
		Long otp=customerDetails.get().getOtp();
		if(!(Objects.equals(otp, buyRequestDto.getOtp()))) {
			log.error(ApplicationConstants.OTP_INVALID_MESSAGE);
			throw new OtpInvalidException(ApplicationConstants.OTP_INVALID_MESSAGE);
		}
		customerOrder.setCustomerId(customerDetails.get());
		customerOrder.setProductId(productDetails.get());
		customerOrder.setPurchasedTime(LocalDateTime.now());
		customerOrderRepository.save(customerOrder);
		Integer updatedQuantity = productAvailableQuantity - productQuantity;
		productDetails.get().setProductQuantity(updatedQuantity);
		productRepository.save(productDetails.get());
		return ApplicationConstants.BUY_SUCCESSMESSAGE;
	}

}
