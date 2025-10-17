package com.medicinedonation.dao;

import com.medicinedonation.model.Donation;
import java.util.List;

public interface DonationDAO {
    void insertDonation(Donation donation) throws Exception;
    void updateDonation(Donation donation) throws Exception;
    void deleteDonation(int donationId) throws Exception;
    Donation getDonationById(int donationId) throws Exception;
    List<Donation> getAllDonations() throws Exception;
    List<Donation> getDonationsByUser(int userId) throws Exception;

}
