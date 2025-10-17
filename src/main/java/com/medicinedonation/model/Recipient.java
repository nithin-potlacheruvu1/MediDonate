package com.medicinedonation.model;

import com.medicinedonation.db.Column;
import com.medicinedonation.db.Table;

@Table(name = "recipients")
public class Recipient implements Trackable {



	@Column(name = "user_id", type = "NUMBER(10)", notNull = true, unique = true)
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Recipient() {
	}

	public Recipient(int userId) {
		super();
		this.userId = userId;
	}

	

	



}
