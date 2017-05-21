package com.aurora.raisedline.dto;

import java.math.BigDecimal;

import com.aurora.raisedline.entities.Product;

public class ProductOrder {
	private Product product;
	private long quantity;
	
	public ProductOrder() {
		super();
	}
	
	public ProductOrder(Product product, long quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getPrice(){
		return product.getProductPrice();
	}
	
	public BigDecimal getAmount(){
		return product.getProductPrice().multiply(new BigDecimal(quantity));
	}
	
}
