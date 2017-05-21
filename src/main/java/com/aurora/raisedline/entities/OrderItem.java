package com.aurora.raisedline.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}), name="order_item")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;
	
	@NotNull
	@JoinColumn(name = "order_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false,
			foreignKey = @ForeignKey(name = "FK_order_item_order"))
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Order order;
	
	@NotNull
	@JoinColumn(name = "product_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false,
			foreignKey = @ForeignKey(name = "FK_order_product"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@NotNull
	@Column(name = "quantity", columnDefinition = "bigint", nullable = false)
	private long quantity;
	
	@NotNull
	@Column(name = "price", columnDefinition = "numeric(10, 2)", nullable = false)
	private BigDecimal price;
	
	@Transient
	private BigDecimal amount;

	public OrderItem() {
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return price.multiply(new BigDecimal(quantity));
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += ((Long)((getProduct().getId()) * 1000000 + getQuantity())).hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        OrderItem other = (OrderItem) object;
        if (!(getOrder().equals(other.getOrder())) || !(getProduct().equals(other.getProduct())))
            return false; 
        return true;
    }

}
