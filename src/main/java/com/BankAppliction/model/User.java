package com.BankAppliction.model;

import javax.persistence.Entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Controller;

import lombok.Data;

@Entity
public class User {
	private String firstName;
	private String SecondName;
	private String emailId;
	private String password;
	public User(String firstName, String secondName, String emailId, String password) {
		super();
		this.firstName = firstName;
		SecondName = secondName;
		this.emailId = emailId;
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSecondName() {
		return SecondName;
	}
	public void setSecondName(String secondName) {
		SecondName = secondName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
