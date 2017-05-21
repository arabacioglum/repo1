package com.aurora.raisedline.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class SmtpMailSender implements MailSender {

	private static final Logger logger = LoggerFactory.getLogger(SmtpMailSender.class);
	
	private JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	@Async
	public void send(String to, String subject, String body) throws MessagingException {
		
		logger.info("Sending SMTP mail from thread " + Thread.currentThread().getName());
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body);
		
		javaMailSender.send(message);
	}


	@Override
	@Async
	public void htmlSend(String to, String subject, String body) throws MessagingException {
		
		logger.info("Sending SMTP mail from thread " + Thread.currentThread().getName());
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		message.setContent(body, "text/html; charset=utf-8");
		message.setSubject(subject);
		message.setRecipients(Message.RecipientType.TO,
	              InternetAddress.parse(to));
//		helper = new MimeMessageHelper(message, true);
//		helper.setSubject(subject);
//		helper.setTo(to);
//		helper.setText(body);
		
		javaMailSender.send(message);
	}

}
