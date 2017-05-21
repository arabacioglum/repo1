package com.aurora.raisedline.entities;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"province_code"}), name="province")
public class Province {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;
	
	@NotNull
	@Column(name = "province_code", columnDefinition = "varchar(4)", nullable = false)
	private String provinceCode;
	
	@NotNull
	@Column(name = "province_name", length = Constants.NAME_MAX, nullable = false)
	private String provinceName;

	public Province() {
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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (provinceCode != null ? provinceCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (object instanceof Province){
        	Province other = (Province) object; 
        	if (!getProvinceCode().equals(other.getProvinceCode())){
                return false; 
        	}
            return true;
        } else{
        	return false;
        }
    }
}
