package com.ecommerce.product.constants;

public class ApplicationConstants {
	private ApplicationConstants() {

	}
	
	public static final String LOGIN_SUCCESS = "Login successfull";
	public static final String LOGIN_FAILURE = "Login not successfull";
	public static final Integer LOGIN_SUCCESS_STATUS = 200;
	public static final Integer LOGIN_FAILURE_STATUS =404;
	
	public static final String CUSTOMER_NOT_EXIST = "Invalid customer email or password";
	
	public static final String ORDER_NOT_FOUND="No order found on this customerId";
	public static final Integer ORDER_FAILURE_STATUS = 404;
	public static final String ORDER_FOUND="Orders on this CustomerId";
	public static final Integer ORDER_SUCCESS_STATUS = 200;

	public static final String PRODUCTLIST_EMPTY_MESSAGE = "Sorry, there are no items matching with your search criteria.";
	public static final Integer PRODUCT_FAILURECODE = 404;

	public static final Integer PRODUCT_SUCCESSCODE = 200;
	public static final String PRODUCTLIST_DISPLAY_MESSAGE = "Products for your serach are being displayed.";

	public static final String BUY_SUCCESSMESSAGE = "Product is successfully purchased";
	public static final String BUY_FAILUREMESSAGE = "Product is not purchased";

	public static final String CUSTOMERNOTFOUND_MESSAGE = "Sorry!!!please check the customer id entered";
	public static final String PRODUCTNOTFOUND_MESSAGE = "Sorry!!!please check the product id entered";
	public static final String PRODUCTQUANTITYINVALID_MESSAGE = "Sorry!!!!there are not much quantity available";

	public static final Boolean TRUE = true;
	public static final Boolean FALSE = false;

	public static final String PURCHASE_CANNOTMESSAGE="Sorry!!!you cannot have shopping now";
	public static final String SUCCESS = "Success";
	public static final String FAILURE = "Failure";
	public static final String OTP_INVALID_MESSAGE="Please check the otp entered";
}
