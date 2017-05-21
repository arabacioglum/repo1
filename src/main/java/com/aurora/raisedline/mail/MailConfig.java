package com.aurora.raisedline.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

	@Value("${mail.sender.host}")
	private String host;
	
	@Value("${smtp.authenticator.email}")
	private String username;

	@Value("${smtp.authenticator.password}")
	private String password;

	@Bean
	@Profile("dev")
	public MailSender mockMailSender(){
		return new MockMailSender();
	}
	
	@Bean
	@Profile("!dev")
	public MailSender smtpMailSender(){
		SmtpMailSender mailSender = new SmtpMailSender();
		mailSender.setJavaMailSender(javaMailSender());
		return mailSender;
	}

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		
		sender.setHost(host );
		sender.setSession(getMailSession());
		return sender;
	}

	private Session getMailSession() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.socketFactory.port", 465); //
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", false);
		return Session.getInstance(properties, getAuthenticator());
	}

	private Authenticator getAuthenticator() {
		SmtpAuthenticator smtpAuthenticator = new SmtpAuthenticator();
		smtpAuthenticator.setUsername(username);
		smtpAuthenticator.setPassword(password);
		return smtpAuthenticator;
	}
}
