package com.aurora.raisedline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
//	
//	@Bean
//    public Validator validator() {
//		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
//		MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
//		factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
//		return factoryBean;
//	}
//	

	private LocalValidatorFactoryBean validator;
	public void setValidator(LocalValidatorFactoryBean validator) {
		this.validator = validator;
	}
    
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }	

	@Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
    	
		MethodValidationPostProcessor processor =
        		new MethodValidationPostProcessor();
        processor.setValidator(validator); //
        return processor;
    } 
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
	}
}
