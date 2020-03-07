package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.constants.ApplicationConstants;
import com.ecommerce.product.dto.BuyRequestDto;
import com.ecommerce.product.dto.BuyResponseDto;
import com.ecommerce.product.dto.ProductList;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.exception.CustomerNotFoundException;
import com.ecommerce.product.exception.OtpInvalidException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductQuantityInvalidException;
import com.ecommerce.product.exception.PurchaseCannotProceedException;
import com.ecommerce.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;


/**
 * Used for displaying product and buying a product
 */

@Slf4j
@RestController
@RequestMapping("/products")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ProductController {
	@Autowired
	ProductService productService;

	/**
	 * @author Muthu
	 * 
	 *         Method is used to display the product details based on the partial
	 *         name entered
	 * 
	 * @param productName
	 * @return ProductResponseDto which contains details of product that includes
	 *         its name,description,quantity available,category and its price
	 */
	@GetMapping
	public ResponseEntity<ProductResponseDto> getProductDetails(@RequestParam String productName) {
		ProductResponseDto productResponseDto = new ProductResponseDto();
		log.info("Searching product details");
		List<ProductList> productList = productService.getProductDetails(productName);
		if (productList.isEmpty()) {
			log.info("Empty list");
			productResponseDto.setMessage(ApplicationConstants.PRODUCTLIST_EMPTY_MESSAGE);
			productResponseDto.setStatusCode(ApplicationConstants.PRODUCT_FAILURECODE);
			return new ResponseEntity<>(productResponseDto, HttpStatus.NOT_FOUND);
		}
		log.info("List being displayed");
		productResponseDto.setMessage(ApplicationConstants.PRODUCTLIST_DISPLAY_MESSAGE);
		productResponseDto.setStatusCode(ApplicationConstants.PRODUCT_SUCCESSCODE);
		productResponseDto.setProductList(productList);
		return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
	}

	/**
	 * @author Muthu
	 * 
	 *         Method is used to buy a product
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
	@PostMapping
	public ResponseEntity<BuyResponseDto> buyProduct(@RequestBody BuyRequestDto buyRequestDto)
			throws CustomerNotFoundException, ProductNotFoundException, ProductQuantityInvalidException,
			PurchaseCannotProceedException, OtpInvalidException {
		BuyResponseDto buyResponseDto = new BuyResponseDto();
		String customerOrder = productService.buyProduct(buyRequestDto);
		if (!(customerOrder.equalsIgnoreCase(ApplicationConstants.BUY_SUCCESSMESSAGE))) {
			log.info("Failed to add to cart");
			buyResponseDto.setStatusCode(ApplicationConstants.PRODUCT_FAILURECODE);
			buyResponseDto.setMessage(ApplicationConstants.BUY_FAILUREMESSAGE);
			return new ResponseEntity<>(buyResponseDto, HttpStatus.NOT_FOUND);
		}
		log.info("Successfully added to cart");
		buyResponseDto.setStatusCode(ApplicationConstants.PRODUCT_SUCCESSCODE);
		buyResponseDto.setMessage(ApplicationConstants.BUY_SUCCESSMESSAGE);
		return new ResponseEntity<>(buyResponseDto, HttpStatus.OK);
	}
}
