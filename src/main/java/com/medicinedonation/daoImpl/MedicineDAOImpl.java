package com.medicinedonation.daoImpl;

import com.medicinedonation.model.Medicine;
import com.medicinedonation.dao.MedicineDAO;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.db.DatabaseSetup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {


	@Override
	public int insertMedicine(Medicine medicine) throws Exception {
		String sql = "INSERT INTO medicines (name, type, expiry_date, quantity, status) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, medicine.getName());
			ps.setString(2, medicine.getType());
			if (medicine.getExpiryDate() != null) {
			    ps.setDate(3, medicine.getExpiryDate());
			} else {
			    ps.setNull(3, java.sql.Types.DATE);
			}
			
			ps.setInt(4, medicine.getQuantity());
			ps.setString(5, medicine.getStatus()); // <-- added
			return ps.executeUpdate();
		}

	}

	@Override
	public int updateMedicine(Medicine medicine) throws Exception {
		String sql = "UPDATE medicines SET name=?, type=?, expiry_date=?, quantity=?, status=? WHERE medicine_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, medicine.getName());
			ps.setString(2, medicine.getType()); // <-- added
			ps.setDate(3, medicine.getExpiryDate());
			ps.setInt(4, medicine.getQuantity());
			ps.setString(5, medicine.getStatus()); // <-- added
			ps.setInt(6, medicine.getMedicineId());
			return ps.executeUpdate();
		}

	}

	@Override
	public int deleteMedicine(int medicineId) throws Exception {
		String sql = "DELETE FROM medicines WHERE medicine_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, medicineId);
			return ps.executeUpdate();
		}
	}

	@Override
	public Medicine getMedicineById(int medicineId) throws Exception {
		String sql = "SELECT * FROM medicines WHERE medicine_id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, medicineId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Medicine(rs.getInt("medicine_id"), rs.getString("name"), rs.getString("type"), // <-- added
						rs.getDate("expiry_date"), rs.getInt("quantity"), rs.getString("status") // <-- added
				);
			}

		}
		return null;
	}

	@Override
	public List<Medicine> getAllMedicines() throws Exception {
		List<Medicine> medicines = new ArrayList<>();
		String sql = "SELECT * FROM medicines";
		try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				medicines.add(new Medicine(rs.getInt("medicine_id"), rs.getString("name"), rs.getString("type"), // <--
																													// added
						rs.getDate("expiry_date"), rs.getInt("quantity"), rs.getString("status") // <-- added
				));
			}
		}
		return medicines;

	}

	public int getMedicineIdByName(String name) throws Exception {
		String sql = "SELECT medicine_id FROM medicines WHERE UPPER(name) = UPPER(?)";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name.trim());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("medicine_id");
			} else {
				throw new Exception("Medicine not found: " + name);
			}
		}
	}

}
