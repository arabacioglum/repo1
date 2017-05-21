package com.aurora.raisedline.entities;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.aurora.raisedline.util.Constants;

@Entity
@Table(name="person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable = false)
	private int ver;
	
	@NotNull
	@Column(name = "first_name", length = Constants.NAME_MAX, nullable = false)
	private String firstName;
	
	@NotNull
	@Column(name = "last_name", length = Constants.NAME_MAX, nullable = false)
	private String lastName;
	
	/*
	@JoinColumn(name = "order_city_id", columnDefinition = "bigint", referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_order_city"))
	@ManyToOne(fetch = FetchType.LAZY)
	private City city;
	*/
	
	@JoinColumn(name = "province_id", columnDefinition = "bigint", referencedColumnName = "id",
			foreignKey = @ForeignKey(name = "FK_person_province"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Province province;
	
	@NotNull
	@Column(name = "address_line1", length = Constants.DESCR_MAX, nullable = false)
	private String addressLine1;
	
	@Column(name = "address_line2", length = Constants.DESCR_MAX)
	private String addressLine2;

	public Person() {
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        Person other = (Person) object;
        if (getId() != other.getId())
            return false; 
        return true;
    }
	
}
