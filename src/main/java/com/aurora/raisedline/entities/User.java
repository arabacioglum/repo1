package com.aurora.raisedline.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.aurora.raisedline.util.Constants;
import com.aurora.raisedline.util.RaisedLineUtil;



@Entity
@Table(name="usr", indexes = {@Index(columnList = "email", unique = true)})
public class User {
	
	public static final int EMAIL_MAX = 255;
	public static final String EMAIL_PATTERN = "[A-Za-z0-9._%-+]+@[A-Za-z0-9._%-+]+\\.[A-Za-z]{2,4}";
	public static final int RANDOM_CODE_LENGTH = 16;
	public static final int PASSWORD_MAX = 50;
	
	public static enum Role {
		UNVERIFIED, BLOCKED, ADMIN
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint")
	private long id;
	
	@Version
	@NotNull
	@Column(name = "ver", columnDefinition = "int", nullable=false)
	private int ver;
	
	@JoinColumn(name = "person_id", referencedColumnName = "id", columnDefinition = "bigint", nullable = false,
			foreignKey = @ForeignKey(name = "FK_user_person"))
	@OneToOne(fetch = FetchType.EAGER)
	private Person person;
	
	@NotNull
	@Column(nullable=false, length=EMAIL_MAX)
	private String email;
	
	@Column(name="user_name", nullable=false, length=Constants.NAME_MAX)
	private String name;
	
	@Column(nullable=false)
	private String password;
	
	@Column(length=16)
	private String verificationCode;
	
	@Column(name="FORGOT_PASSWORD_CODE", length = RANDOM_CODE_LENGTH)
	private String forgotPasswordCode;
	
	@ElementCollection(fetch=FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getForgotPasswordCode() {
		return forgotPasswordCode;
	}

	public void setForgotPasswordCode(String forgotPasswordCode) {
		this.forgotPasswordCode = forgotPasswordCode;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}
	
	public boolean isEditable() {
		User loggedIn = RaisedLineUtil.getSessionUser();
		return loggedIn != null &&
				(loggedIn.getId() == this.getId() || loggedIn.isAdmin());
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        User other = (User) object;
        if (!getEmail().equals(other.getEmail()))
            return false; 
        return true;
    }
}
