package com.medicinedonation.daoImpl;

import com.medicinedonation.model.Donation;
import com.medicinedonation.dao.DonationDAO;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.db.DatabaseSetup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationDAOImpl implements DonationDAO {

	@Override
	public void insertDonation(Donation donation) throws Exception {
		String sql = "INSERT INTO donations (user_id, medicine_id, quantity, donation_date, medicine_type, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
		    ps.setInt(1, donation.getUserId());
		    ps.setInt(2, donation.getMedicineId());
		    ps.setInt(3, donation.getQuantity());
		    ps.setDate(4, donation.getDonationDate());
		    ps.setString(5, donation.getMedicineType());
		    ps.setDate(6, donation.getExpiryDate());
		    int rows = ps.executeUpdate();
		    System.out.println("Inserted rows: " + rows);
		}

	}

	@Override
	public void updateDonation(Donation donation) throws Exception {
		String sql = "UPDATE donations SET user_id=?, medicine_id=?, quantity=?, donation_date=?, medicine_type=?, expiry_date=? WHERE donation_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, donation.getUserId());
			ps.setInt(2, donation.getMedicineId());
			ps.setInt(3, donation.getQuantity());
			ps.setDate(4, donation.getDonationDate());
			ps.setString(5, donation.getMedicineType());
			ps.setDate(6, donation.getExpiryDate()); // <-- add this
			ps.setInt(7, donation.getDonationId());
		}
	}

	@Override
	public void deleteDonation(int donationId) throws Exception {
		String sql = "DELETE FROM donations WHERE donation_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, donationId);
			ps.executeUpdate();
		}
	}

	@Override
	public Donation getDonationById(int donationId) throws Exception {
	    String sql = "SELECT * FROM donations WHERE donation_id=?";
	    try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, donationId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            Donation d = new Donation(
	                rs.getInt("donation_id"),
	                rs.getInt("user_id"),
	                rs.getInt("medicine_id"),
	                rs.getInt("quantity"),
	                rs.getDate("donation_date"),
	                rs.getString("medicine_type"),
	                rs.getDate("expiry_date")
	            );
	            return d;
	        }
	    }
	    return null;
	}


	@Override
	public List<Donation> getAllDonations() throws Exception {
	    List<Donation> donations = new ArrayList<>();
	    String sql = "SELECT * FROM donations";
	    try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
	        ResultSet rs = stmt.executeQuery(sql);
	        while (rs.next()) {
	            Donation d = new Donation(
	                rs.getInt("donation_id"),
	                rs.getInt("user_id"),
	                rs.getInt("medicine_id"),
	                rs.getInt("quantity"),
	                rs.getDate("donation_date"),
	                rs.getString("medicine_type"),
	                rs.getDate("expiry_date")
	            );
	            donations.add(d);
	        }
	    }
	    return donations;
	}



	@Override
	public List<Donation> getDonationsByUser(int userId) throws Exception {
	    List<Donation> donations = new ArrayList<>();
	    String sql = "SELECT * FROM donations WHERE user_id=?";
	    try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Donation d = new Donation(
	                rs.getInt("donation_id"),
	                rs.getInt("user_id"),
	                rs.getInt("medicine_id"),
	                rs.getInt("quantity"),
	                rs.getDate("donation_date"),
	                rs.getString("medicine_type"),
	                rs.getDate("expiry_date")
	            );
	            donations.add(d);
	        }
	    }
	    return donations;
	}



}
