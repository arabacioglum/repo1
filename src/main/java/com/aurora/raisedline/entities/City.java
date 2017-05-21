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
@Table(name="city")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;
	
	@NotNull
	@Column(name = "city_name", length = Constants.NAME_MAX, nullable = false)
	private String cityName;
	
	@NotNull
	@JoinColumn(name = "province_code", referencedColumnName = "province_code", columnDefinition = "varchar(4)", nullable = false,
			foreignKey = @ForeignKey(name = "FK_city_province"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Province province;

	public City() {
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
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
        City other = (City) object;
        if (getId() != other.getId())
            return false; 
        return true;
    }

}
