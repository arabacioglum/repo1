package com.aurora.raisedline.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class RequestMonitor {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestMonitor.class);
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object wrap(ProceedingJoinPoint pjp) throws Throwable{
		logger.info("Before Controller Method " + pjp.getSignature().getName() + ". Thread " 
				+ Thread.currentThread().getName());
		Object retVal = pjp.proceed();
		logger.info("Controller Method " + pjp.getSignature().getName() + " execution successful.");
		return retVal;
	}
}
