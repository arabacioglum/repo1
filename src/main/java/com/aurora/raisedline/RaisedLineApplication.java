package com.aurora.raisedline;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class RaisedLineApplication {
	private static final Logger logger = LoggerFactory.getLogger(RaisedLineApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RaisedLineApplication.class, args);
		
		String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		
		for(String beanName : beanNames){
			logger.info(beanName);
		}
	}
}
