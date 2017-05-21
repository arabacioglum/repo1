package com.aurora.raisedline.services;

import com.aurora.raisedline.dto.OrderForm;

public interface OrderService {
	public void insertOrder(OrderForm orderForm) throws Exception;
}
