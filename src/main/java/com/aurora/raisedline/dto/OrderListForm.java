package com.aurora.raisedline.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderListForm {
	private List<ProductTrio> productTrios;

	public OrderListForm() {
		super();
		productTrios = new ArrayList<>();
	}

	public List<ProductTrio> getProductTrios() {
		return productTrios;
	}

	public void setProductTrios(List<ProductTrio> productTrios) {
		this.productTrios = productTrios;
	}

}
