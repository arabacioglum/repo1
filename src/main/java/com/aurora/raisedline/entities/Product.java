package com.aurora.raisedline.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.aurora.raisedline.util.Constants;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id"}), name="product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;

	@NotNull
	@Column(name = "product_id", length = Constants.ID_MAX, nullable=false, unique = true)
	private String productId;

	@NotNull
	@Column(name = "product_name", length = Constants.NAME_MAX, nullable=false)
	private String productName;
	
	@Column(name = "product_description", length = Constants.DESCR_MAX)
	private String productDescription;
	
	@Column(name = "product_picture", length = Constants.NAME_MAX)
	private String productPicture;
	
	@Column(name = "product_price", columnDefinition = "numeric(10, 2) default 0", nullable = false)
	private BigDecimal productPrice;

	public Product() {
	}

	public Product(String productId, String productName) {
		this.productId = productId;
		this.productName = productName;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        Product other = (Product) object;
        if (!getProductId().equals(other.getProductId()))
            return false; 
        return true;
    }
	
}
