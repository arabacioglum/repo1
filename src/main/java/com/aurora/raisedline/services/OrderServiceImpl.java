package com.aurora.raisedline.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.aurora.raisedline.controllers.RootController;
import com.aurora.raisedline.dto.OrderForm;
import com.aurora.raisedline.dto.ProductOrder;
import com.aurora.raisedline.entities.Order;
import com.aurora.raisedline.entities.OrderItem;
import com.aurora.raisedline.mail.MailSender;
import com.aurora.raisedline.repositories.OrderRepository;
import com.aurora.raisedline.util.RaisedLineUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
//	private final static Locale locale = new Locale("tr", "TR");

	private OrderRepository orderRepository;
	private MessageSource messageSource;

	private MailSender mailSender;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, MailSender mailSender, MessageSource messageSource) {
		this.orderRepository = orderRepository;
		this.mailSender = mailSender;
		this.messageSource = messageSource;
	}
	
	@Override
	public void insertOrder(OrderForm orderForm) throws Exception {
		try {
			Order order = new Order();
			order.setFirstName(orderForm.getFirstName());
			order.setInvoiceAddressLine1(orderForm.getInvoiceAddressLine1());
			order.setInvoiceAddressLine2(orderForm.getInvoiceAddressLine2());
			order.setInvoiceProvince(orderForm.getInvoiceProvince());
			order.setLastName(orderForm.getLastName());
			order.setOrderAddressLine1(orderForm.getAddressLine1());
			order.setOrderDate(new Date());
			order.setOrderNumber(calcOrderNumber());
			order.setOrderProvince(orderForm.getProvince());
			order.setPaymentMethod(orderForm.getPaymentMethod());
			if (orderForm.getUser() != null){
				order.setUser(orderForm.getUser());
			} /*else {
				order.setUser(userService.registerReturnUser(orderForm));
			}*/
			Set<OrderItem> orderItems = new HashSet<>();
			for (ProductOrder pOrder : orderForm.getProductOrders()){
				OrderItem orderItem = new OrderItem();
				orderItem.setPrice(pOrder.getPrice());
				orderItem.setProduct(pOrder.getProduct());
				orderItem.setQuantity(pOrder.getQuantity());
				orderItems.add(orderItem);
			}
			order.setOrderItems(orderItems);
			orderRepository.save(order);
			TransactionSynchronizationManager.registerSynchronization(
				new TransactionSynchronizationAdapter() {
					@Override
					public void afterCommit(){
						try {
							mailSender.htmlSend(orderForm.getEmail(), RaisedLineUtil.getMessage("orderInfoMail"), 
									prepareMailBody());
						} catch (MessagingException e) {
							logger.error(ExceptionUtils.getStackTrace(e));
							logger.info("Verification mail to " + orderForm.getEmail() + " is queued");
						}
					}

					private String prepareMailBody() {
						Locale locale = new Locale("tr", "TR");
						int i = 0;
						StringBuilder sBuilder = new StringBuilder();
						String[] args = {orderForm.getFirstName(), orderForm.getLastName(), ""};
						sBuilder.append(messageSource.getMessage("text.sayin", args, locale));
						sBuilder.append("<P><H2>");
						sBuilder.append(messageSource.getMessage("text.headerOrder", args, locale));
						sBuilder.append("</H2>");
						for (ProductOrder pOrder : orderForm.getProductOrders()){
							args[0] = "" + pOrder.getQuantity();
							args[1] = pOrder.getProduct().getProductName();
							args[2] = "" + pOrder.getAmount();
							sBuilder.append("<P>");
							sBuilder.append(messageSource.getMessage("text.orderItem", args, locale));
						}
						args[0] = "" + orderForm.getTotalAmount();
						sBuilder.append("<P>");
						sBuilder.append(messageSource.getMessage("text.orderFooter", args, locale));
						
						return sBuilder.toString();
					}
				}
			);
			logger.info("Siparis girilmistir!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int calcOrderNumber() {
		return orderRepository.findMaxOrderNumber() + 1;
	}

}
