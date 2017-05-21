package com.aurora.raisedline.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.aurora.raisedline.util.Constants;
import com.aurora.raisedline.util.Constants.PaymentMethod;
import com.aurora.raisedline.util.RaisedLineUtil;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"order_date", "order_number"}), name="order_header")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	@Column(name = "order_date", columnDefinition = "date", nullable = false)
	private Date orderDate;
	
	@NotNull
	@Column(name = "order_number", columnDefinition = "int", nullable = false)
	private int orderNumber;

	@JoinColumn(name = "user_id", columnDefinition = "bigint", referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_order_user"))
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name = "order_first_name", length = Constants.NAME_MAX)
	private String firstName;
	
	@Column(name = "order_last_name", length = Constants.NAME_MAX)
	private String lastName;
	
	@JoinColumn(name = "order_province_id", columnDefinition = "bigint", referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_order_province"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Province orderProvince;
	
	@Column(name = "order_address_line1", length = Constants.DESCR_MAX)
	private String orderAddressLine1;
	
	@Column(name = "order_address_line2", length = Constants.DESCR_MAX)
	private String orderAddressLine2;
	
	@JoinColumn(name = "invoice_province_id", columnDefinition = "bigint", referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_invoice_province"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Province invoiceProvince;
	
	@Column(name = "invoice_address_line1", length = Constants.DESCR_MAX)
	private String invoiceAddressLine1;
	
	@Column(name = "invoice_address_line2", length = Constants.DESCR_MAX)
	private String invoiceAddressLine2;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	@Column(name = "payment_method", columnDefinition = "tinyint", nullable = false)
	private PaymentMethod paymentMethod;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
	private Set<OrderItem> orderItems;
	
	@PrePersist
    private void prePersistMethod() {
        for (OrderItem oi : orderItems) {
            oi.setOrder(this);
        }
    }

    @PreUpdate
    private void preUpdateMethod() {
        for (OrderItem oi : orderItems) {
            oi.setOrder(this);
        }
    }
	public Order() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

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

	public Province getOrderProvince() {
		return orderProvince;
	}

	public void setOrderProvince(Province orderProvince) {
		this.orderProvince = orderProvince;
	}

	public String getOrderAddressLine1() {
		return orderAddressLine1;
	}

	public void setOrderAddressLine1(String orderAddressLine1) {
		this.orderAddressLine1 = orderAddressLine1;
	}

	public String getOrderAddressLine2() {
		return orderAddressLine2;
	}

	public void setOrderAddressLine2(String orderAddressLine2) {
		this.orderAddressLine2 = orderAddressLine2;
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

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (getId().hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        Order other = (Order) object;
        if ((RaisedLineUtil.compareDates(getOrderDate(), other.getOrderDate()) != 0) || (getOrderNumber() != other.getOrderNumber()))
            return false; 
        return true;
    }
	
}
