package com.medicinedonation.model;

import com.medicinedonation.db.Column;
import com.medicinedonation.db.Table;

@Table(name = "medicines")
public class Medicine {

	@Column(name = "medicine_id", type = "NUMBER(10)", primaryKey = true, autoIncrement = true)
	private int medicineId;

	@Column(name = "name", type = "VARCHAR2(100)", notNull = true)
	private String name;

	@Column(name = "type", type = "VARCHAR2(50)")
	private String type;

	@Column(name = "expiry_date", type = "DATE", notNull = false)
	private java.sql.Date expiryDate;

	@Column(name = "quantity", type = "NUMBER(10)", notNull = true)
	private int quantity;

	@Column(name = "status", type = "VARCHAR2(20)")
	private String status;

	public Medicine() {
	}

	// Constructor for new medicines (without ID)
	public Medicine(String name, String type, java.time.LocalDate expiryDate, int quantity, String status) {
		this.name = name;
		this.type = type;
		this.expiryDate = java.sql.Date.valueOf(expiryDate);
		this.quantity = quantity;
		this.status = status;
	}

	// Full constructor (with ID, e.g. when reading from DB)
	public Medicine(int medicineId, String name, String type, java.sql.Date expiryDate, int quantity, String status) {
		this.medicineId = medicineId;
		this.name = name;
		this.type = type;
		this.expiryDate = expiryDate;
		this.quantity = quantity;
		this.status = status;
	}

	// Getters and setters
	public int getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.sql.Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(java.sql.Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Medicine [medicineId=" + medicineId + ", name=" + name + ", type=" + type + ", expiryDate=" + expiryDate
				+ ", quantity=" + quantity + ", status=" + status + "]";
	}

	public Medicine(String name, String type, int quantity, String status) {
		super();
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.status = status;
	}
	
	
	
}
