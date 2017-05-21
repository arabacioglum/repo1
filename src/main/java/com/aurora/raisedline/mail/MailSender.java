package com.aurora.raisedline.mail;

import javax.mail.MessagingException;

public interface MailSender {

	public void send(String to, String subject, String body) throws MessagingException;
	public void htmlSend(String to, String subject, String body) throws MessagingException;

}