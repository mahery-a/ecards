package com.quarrion.ecards.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.quarrion.ecards.model.Ecard;

/**
 * 
 *
 */
@Service
public class MailService {

	/*
	 * The Spring Framework provides an easy abstraction for sending email by using
	 * the JavaMailSender interface, and Spring Boot provides auto-configuration for
	 * it as well as a starter module.
	 */
	private JavaMailSender javaMailSender;

	/**
	 * 
	 * @param javaMailSender
	 */
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendEcard(Ecard ecard) throws MailException, MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
		String mediaObject = "<img src=\"" + ecard.getMediaUrl() + "\"/>";
		String htmlMsg =  mediaObject + ecard.getText();
		/*String htmlMsg = "<h1>this is a test</h1>" + 
				"<img src=\"https://media.giphy.com/media/klxOW1G2uiVYg8axes/giphy.gif\" alt=\"\" width=\"308\" height=\"173\" />" + 
				"<h4><em><strong><span style=\"color: #ff0000;\">this is a test</span></strong></em></h4>";*/
		mimeMessage.setContent(htmlMsg, "text/html");
		helper.setTo(ecard.getDestinationEmail());
		helper.setSubject(ecard.getTitle());
		helper.setFrom("blabla@hotmail.ca");
		
		// WITH ATTACHMENT
		//FileSystemResource file = new FileSystemResource("/home/rockhard/Desktop/Registration.pdf");
		//helper.addAttachment(file.getFilename(), file);
		
		javaMailSender.send(mimeMessage);
	}
}
