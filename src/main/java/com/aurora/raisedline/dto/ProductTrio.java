package com.aurora.raisedline.dto;

public class ProductTrio{
	ProductOrder productOrder0;
	ProductOrder productOrder1;
	ProductOrder productOrder2;
	
	public ProductTrio(){
		this.productOrder0 = new ProductOrder();
		this.productOrder1 = new ProductOrder();
		this.productOrder2 = new ProductOrder();
	}

	public ProductOrder getProductOrder0() {
		return productOrder0;
	}

	public void setProductOrder0(ProductOrder productOrder0) {
		this.productOrder0 = productOrder0;
	}

	public ProductOrder getProductOrder1() {
		return productOrder1;
	}

	public void setProductOrder1(ProductOrder productOrder1) {
		this.productOrder1 = productOrder1;
	}

	public ProductOrder getProductOrder2() {
		return productOrder2;
	}

	public void setProductOrder2(ProductOrder productOrder2) {
		this.productOrder2 = productOrder2;
	}

}
