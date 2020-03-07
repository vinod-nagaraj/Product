package com.ecommerce.product.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
/**
 * This Utility is used to perform Mail Related operations.
 * @author Chethana M
 *
 */
@Component
public class SendMail {
	@Autowired
	private MailSender mailSender;

	/**
	 * This class is used to compose the email body with recipients
	 * 
	 * @author Chethana
	 * @param toAddress of type String address to which mail needs to be sent
	 * @param subject   of type String Subject on which mail needs to be sent
	 * @param msgBody   of type String which is the generated OTP
	 */
	public void SendMailToCustomer(String toAddress, String subject, String msgBody) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("muthupalaniappan552@gmail.com");
		msg.setTo(toAddress);
		msg.setSubject(subject);
		msg.setText(msgBody);
		mailSender.send(msg);
	}
}