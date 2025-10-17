package com.medicinedonation.model;

import com.medicinedonation.db.Table;
import com.medicinedonation.db.Column;

@Table(name = "donors")
public class Donor implements Trackable {

	@Column(name = "points", type = "NUMBER(10)")
	private int points;

	@Column(name = "user_id", type = "NUMBER(10)", notNull = true)
	private int userId;

	public Donor() {
	}

	public Donor(int userId, int points) {
		this.userId = userId;
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Donor [points=" + points + ", userId=" + userId + "]";
	}

	
	
	
}
