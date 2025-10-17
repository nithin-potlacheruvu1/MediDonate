package com.medicinedonation.model;

import com.medicinedonation.db.Table;
import com.medicinedonation.db.Column;

@Table(name = "USERS")
public class User implements Trackable {

	@Column(name = "USER_ID", type = "NUMBER(10)", primaryKey = true, autoIncrement = true)
	private int userId;

	@Column(name = "EMAIL", type = "VARCHAR2(100)", unique = true, notNull = true)
	private String email;

	@Column(name = "PASSWORD", type = "VARCHAR2(100)", notNull = true)
	private String password;

	@Column(name = "PHONE", type = "VARCHAR2(20)")
	private String phone;

	@Column(name = "ADDRESS", type = "VARCHAR2(200)")
	private String address;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// Default constructor
	public User() {
	}

	// Parameterized constructor
	public User(int userId, String email, String password) {
		this.userId = userId;
		this.email = email;
		this.password = password;
	}

	// Getters and Setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
