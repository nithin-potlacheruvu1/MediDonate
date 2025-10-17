package com.medicinedonation.service;

import java.util.List;

import com.medicinedonation.dao.DonorDAO;
import com.medicinedonation.daoImpl.DonorDAOImpl;
import com.medicinedonation.model.Donor;

public class DonorManager {
    private DonorDAO donorDAO = new DonorDAOImpl();

    // Add a new donor
    public int addDonor(Donor donor) throws Exception {
        // Check if userId already exists
        List<Donor> allDonors = donorDAO.getAllDonors();
        for (Donor d : allDonors) {
            if (d.getUserId() == donor.getUserId()) {
                return -1; // user already exists
            }
        }
        return donorDAO.insertDonor(donor); // returns number of rows inserted
    }

    // Update donor points
    public void updateDonor(Donor donor) {
        try {
            int rows = donorDAO.updateDonor(donor);
            if (rows > 0) {
                System.out.println("Donor updated!");
            } else {
                System.out.println("Donor with userId " + donor.getUserId() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error updating donor: " + e.getMessage());
        }
    }

    // Remove donor
    public void removeDonor(int userId) {
        try {
            int rows = donorDAO.deleteDonor(userId);
            if (rows > 0) {
                System.out.println("Donor removed!");
            } else {
                System.out.println("Donor with userId " + userId + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error removing donor: " + e.getMessage());
        }
    }

    // Get donor by userId
    public Donor getDonorByUserId(int userId) throws Exception {
        return donorDAO.getDonorByUserId(userId);
    }

    // Get all donors
    public List<Donor> getAllDonors() throws Exception {
        return donorDAO.getAllDonors();
    }
}
