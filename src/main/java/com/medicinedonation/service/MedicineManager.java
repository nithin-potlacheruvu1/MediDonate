package com.medicinedonation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.medicinedonation.dao.MedicineDAO;
import com.medicinedonation.daoImpl.MedicineDAOImpl;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.Medicine;

public class MedicineManager {
    private MedicineDAO medicineDAO = new MedicineDAOImpl();

    public void addMedicine(Medicine medicine) {
        try {
            int rows = medicineDAO.insertMedicine(medicine);
            if (rows > 0) {
                System.out.println("Medicine added successfully!");
            } else {
                System.out.println("Failed to add medicine!");
            }
        } catch (Exception e) {
            System.out.println("Error adding medicine: " + e.getMessage());
        }
    }

    public void updateMedicine(Medicine medicine) {
        try {
            int rows = medicineDAO.updateMedicine(medicine);
            if (rows > 0) {
                System.out.println("Medicine updated successfully!");
            } else {
                System.out.println("Medicine ID " + medicine.getMedicineId() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error updating medicine: " + e.getMessage());
        }
    }

    public void removeMedicine(int medicineId) {
        try {
            int rows = medicineDAO.deleteMedicine(medicineId);
            if (rows > 0) {
                System.out.println("Medicine removed successfully!");
            } else {
                System.out.println("Medicine ID " + medicineId + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error removing medicine: " + e.getMessage());
        }
    }


    public Medicine getMedicine(int medicineId) throws Exception {
        return medicineDAO.getMedicineById(medicineId);
    }

    public List<Medicine> getAllMedicines() throws Exception {
        return medicineDAO.getAllMedicines();
    }
    public int getMedicineIdByName(String name){
        try {
			return medicineDAO.getMedicineIdByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
    }
    public Medicine getMedicineByName(String name) throws Exception {
        List<Medicine> all = medicineDAO.getAllMedicines();
        for (Medicine m : all) {
        	if (m.getName().equalsIgnoreCase(name.trim())) {
        	    return m;
        	}
        }
        throw new Exception("Medicine not found: " + name);
    }
    public String getMedicineNameById(int medicineId) {
        try {
            Medicine med = getMedicine(medicineId);  // existing method
            return med != null ? med.getName() : "Unknown";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public boolean isDuplicateMedicine(String name, String type) {
        try {
            List<Medicine> all = medicineDAO.getAllMedicines();
            for (Medicine m : all) {
                if (m.getName().equalsIgnoreCase(name.trim()) &&
                    m.getType().equalsIgnoreCase(type.trim())) {
                    return true; 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; 
    }
    public Medicine getMedicineByNameAndType(String name, String type) throws Exception {
        String sql = "SELECT * FROM medicines WHERE LOWER(name)=LOWER(?) AND LOWER(type)=LOWER(?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setString(2, type.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Medicine(
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDate("expiry_date"),
                    rs.getInt("quantity"),
                    rs.getString("status")
                );
            }
        }
        return null;
    }


}
