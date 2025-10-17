package com.medicinedonation.model;

import com.medicinedonation.db.Column;
import com.medicinedonation.db.Table;
import java.sql.Date;

@Table(name = "requests")
public class Request {

	@Column(name = "request_id", type = "NUMBER(10)", primaryKey = true, autoIncrement = true)
	private int requestId;

	@Column(name = "medicine_id", type = "NUMBER(10)", notNull = true)
	private int medicineId;

	@Column(name = "quantity", type = "NUMBER(10)", notNull = true)
	private int quantity;

	@Column(name = "request_date", type = "DATE", notNull = true)
	private Date requestDate;

	@Column(name = "status", type = "VARCHAR2(50)", notNull = true)
	private String status;
	@Column(name = "user_id", type = "NUMBER(10)", notNull = true)
	private int userId;
	public Request() {
	}
	

	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public Request(int requestId, int medicineId, int quantity, Date requestDate, String status, int userId) {
		super();
		this.requestId = requestId;
		this.medicineId = medicineId;
		this.quantity = quantity;
		this.requestDate = requestDate;
		this.status = status;
		this.userId = userId;
	}


	public Request(int requestId,int medicineId, int quantity, Date requestDate, String status) {
		super();
		this.requestId = requestId;
		this.medicineId = medicineId;
		this.quantity = quantity;
		this.requestDate = requestDate;
		this.status = status;
	}

	public Request(int medicineId, int quantity, Date requestDate, String status) {
		this.medicineId = medicineId;
		this.quantity = quantity;
		this.requestDate = requestDate;
		this.status = status;
	}

	
	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public static class Validator {
		public static void validate(Request request) throws Exception {
			if (request.getMedicineId() <= 0) {
				throw new Exception("Medicine must be selected.");
			}
			if (request.getQuantity() <= 0) {
				throw new Exception("Quantity must be positive.");
			}

			
		}
	}
}
