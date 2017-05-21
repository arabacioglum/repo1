package com.aurora.raisedline.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.aurora.raisedline.entities.Province;
import com.aurora.raisedline.entities.User;
import com.aurora.raisedline.util.Constants.PaymentMethod;

public class OrderForm {
	
	private User user;
	
	@NotNull
	@Size(min=1, max=50,
			message="{nameSizeError}")
	private String firstName;
	
	@NotNull
	@Size(min=1, max=50,
			message="{nameSizeError}")
	private String lastName;
	
	@NotNull
	private Province province;
	
	@NotNull
	@Size(min=1, max=255, 
			message="{nameSizeError}")
	private String addressLine1;
	
	@Size(max=255, 
			message="{nameSizeError}")
	private String addressLine2;
	
	private Province invoiceProvince;
	
	private String invoiceAddressLine1;
	
	private String invoiceAddressLine2;
	
	private PaymentMethod paymentMethod;
	
	private BigDecimal totalAmount;
	
	@NotNull
	@Size(min=1, max=255)
	@Pattern(regexp=User.EMAIL_PATTERN,
				message="{emailPatternError}")
	private String email;
	
	private List<ProductOrder> productOrders;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public Province getInvoiceProvince() {
		return invoiceProvince;
	}

	public void setInvoiceProvince(Province invoiceProvince) {
		this.invoiceProvince = invoiceProvince;
	}

	public String getInvoiceAddressLine1() {
		return invoiceAddressLine1;
	}

	public void setInvoiceAddressLine1(String invoiceAddressLine1) {
		this.invoiceAddressLine1 = invoiceAddressLine1;
	}

	public String getInvoiceAddressLine2() {
		return invoiceAddressLine2;
	}

	public void setInvoiceAddressLine2(String invoiceAddressLine2) {
		this.invoiceAddressLine2 = invoiceAddressLine2;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public void setTotalAmount(BigDecimal totalAmount){
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public List<ProductOrder> getProductOrders() {
		return productOrders;
	}

	public void setProductOrders(List<ProductOrder> productOrders) {
		this.productOrders = productOrders;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
