package com.medicinedonation.model;

import java.sql.Date;

import com.medicinedonation.db.Column;
import com.medicinedonation.db.Table;

@Table(name = "donations")
public class Donation implements Trackable {

	@Column(name = "user_id", type = "NUMBER(10)", notNull = true)
	private int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "donation_id", type = "NUMBER(10)", primaryKey = true, autoIncrement = true)
	private int donationId;


	@Column(name = "medicine_id", type = "NUMBER(10)", notNull = true)
	private int medicineId;

	@Column(name = "quantity", type = "NUMBER(10)", notNull = true)
	private int quantity;

	@Column(name = "donation_date", type = "DATE", notNull = true)
	private java.sql.Date donationDate;
	
	@Column(name = "medicine_type", type = "VARCHAR2(50)", notNull = true)
	private String medicineType;

	public String getMedicineType() {
	    return medicineType;
	}

	public void setMedicineType(String medicineType) {
	    this.medicineType = medicineType;
	}

	public Donation() {
	}
	
	@Column(name = "expiry_date", type = "DATE", notNull = true)
	private java.sql.Date expiryDate;
	
	
	public java.sql.Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(java.sql.Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	

	public Donation(int donationId, int userId, int medicineId, int quantity, Date donationDate, String medicineType, Date expiryDate) {
	    this.donationId = donationId;
	    this.userId = userId;
	    this.medicineId = medicineId;
	    this.quantity = quantity;
	    this.donationDate = donationDate;
	    this.medicineType = medicineType;
	    this.expiryDate = expiryDate;
	}

	public Donation(int donationId,int medicineId, int quantity, java.sql.Date donationDate) {
		this.donationId = donationId;
		this.medicineId = medicineId;
		this.quantity = quantity;
		this.donationDate = donationDate;
	}

	// Getters and Setters
	public int getDonationId() {
		return donationId;
	}

	public void setDonationId(int donationId) {
		this.donationId = donationId;
	}



	public int getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public java.sql.Date getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(java.sql.Date donationDate) {
		this.donationDate = donationDate;
	}

	public static class Validator {
		public static void validate(Donation donation) throws Exception {
			if (donation.getMedicineId() <= 0) {
				throw new Exception("Medicine must be selected.");
			}
			if (donation.getQuantity() <= 0) {
				throw new Exception("Quantity must be positive.");
			}
			if (donation.getExpiryDate() == null) {
			    throw new Exception("Expiry date must be provided.");
			}

		}
	}
	public Donation(int donationId, int userId, int medicineId, int quantity, java.sql.Date donationDate) {
	    this.donationId = donationId;
	    this.userId = userId;
	    this.medicineId = medicineId;
	    this.quantity = quantity;
	    this.donationDate = donationDate;
	}
}
