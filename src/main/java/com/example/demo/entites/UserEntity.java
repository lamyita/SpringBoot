package com.example.demo.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name="users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6949828312801015558L;

	@Id  //prim key
	@GeneratedValue /// auto incrmt
	private long id;
	
 
	@Column(nullable=false, length=50) ////not null
	private String userId;
	
	@Column(nullable=false, length=50) ////not null
	private String firstName;
	@Column(nullable=false, length=50) ////not null
	private String lastName;
	@Column(nullable=false, length=120, unique=true)
	private String email;
	@Column(nullable= false)
	private String encryptedPassword;
	@Column(nullable=true)
	private String emailVeificationToken;
	///	@Column(columnDefinition="boolen defult false") ////discrption
	@Column(nullable= false)
	private Boolean emailVerificationStatus = false ; 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailVeificationToken() {
		return emailVeificationToken;
	}
	public void setEmailVeificationToken(String emailVeificationToken) {
		this.emailVeificationToken = emailVeificationToken;
	}
	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}
	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	
	
}
