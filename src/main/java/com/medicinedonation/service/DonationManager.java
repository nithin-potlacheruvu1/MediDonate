package com.medicinedonation.service;

import java.util.List;

import com.medicinedonation.dao.DonationDAO;
import com.medicinedonation.daoImpl.DonationDAOImpl;
import com.medicinedonation.model.Donation;
import com.medicinedonation.model.User;

public class DonationManager {
    private DonationDAO donationDAO = new DonationDAOImpl();
    private UserManager userManager = new UserManager();

    public void addDonation(Donation donation) throws Exception {
    	Donation.Validator.validate(donation);
        donationDAO.insertDonation(donation);

        // Notify donor
        User donor = userManager.getUser(donation.getUserId());

        NotificationService service = new NotificationService();
        service.notifyUser(donor, "Your donatison is recorded!");
    }

    public void updateDonation(Donation donation) throws Exception {
        donationDAO.updateDonation(donation);

        // Notify donor
        User donor = userManager.getUser(donation.getUserId());
        NotificationService notificationService = new NotificationService();
        notificationService.notifyUser(donor, "Your donation details have been updated.");
    }

    public void removeDonation(int donationId) throws Exception {
        donationDAO.deleteDonation(donationId);
    }

    public Donation getDonation(int donationId) throws Exception {
        return donationDAO.getDonationById(donationId);
    }

    public List<Donation> getAllDonations() throws Exception {
        return donationDAO.getAllDonations();
    }

    public List<Donation> getDonationsByUser(int userId) throws Exception {
        return donationDAO.getDonationsByUser(userId);
    }
    public boolean isDuplicateDonation(Donation donation) {
        try {
            List<Donation> donations = getDonationsByUser(donation.getUserId());
            for (Donation d : donations) {
                if (d.getMedicineId() == donation.getMedicineId() &&
                    d.getMedicineType().equalsIgnoreCase(donation.getMedicineType()) &&
                    d.getQuantity() == donation.getQuantity() &&
                    d.getDonationDate().equals(donation.getDonationDate())) {
                    return true; // duplicate found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // no duplicate
    }

}
