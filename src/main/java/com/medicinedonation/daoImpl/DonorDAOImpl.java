package com.medicinedonation.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.medicinedonation.dao.DonorDAO;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.Donor;

public class DonorDAOImpl implements DonorDAO {

	@Override
	public int insertDonor(Donor donor) throws SQLException, Exception {
		// After inserting, fetch the max donor_id for this user
		String sql = "INSERT INTO donors (user_id, points) VALUES (?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, donor.getUserId());
			ps.setInt(2, donor.getPoints());

			int rows = ps.executeUpdate();

			return rows;
		}

	}

	@Override
	public int updateDonor(Donor donor) {
		int rows = 0; // declare outside try
		String sql = "UPDATE donors SET points=? WHERE user_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, donor.getPoints());
			ps.setInt(2, donor.getUserId());

			rows = ps.executeUpdate(); // assign number of updated rows

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows; // always return
	}

	@Override
	public int deleteDonor(int userId) {
	    int rows = 0;
	    String sql = "DELETE FROM donors WHERE user_id=?";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, userId); 
	        rows = ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return rows;
	}


	

	@Override
	public List<Donor> getAllDonors() {
		List<Donor> donors = new ArrayList<>();
		String sql = "SELECT * FROM donors";
		try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Donor donor = new Donor();
				donor.setUserId(rs.getInt("user_id"));
				donor.setPoints(rs.getInt("points"));
				donors.add(donor);
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return donors; // always returns a list
	}

	@Override
	public Donor getDonorByUserId(int userId) throws Exception {
		Donor donor = null;
		String sql = "SELECT * FROM donors WHERE user_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				donor = new Donor();
				donor.setUserId(rs.getInt("user_id"));
				donor.setPoints(rs.getInt("points"));
			}

			rs.close();
		}
		return donor;
	}



}
