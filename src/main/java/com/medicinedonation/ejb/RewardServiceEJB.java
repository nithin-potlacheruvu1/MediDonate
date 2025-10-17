package com.medicinedonation.ejb;

import javax.ejb.Stateless;

import com.medicinedonation.model.Donor;
import com.medicinedonation.service.DonorManager;

@Stateless
public class RewardServiceEJB implements RewardServiceEJBRemote {

    private DonorManager donorManager = new DonorManager();

    @Override
    public void addRewardPoints(int userId, String medicineType, int quantity) throws Exception {
        Donor donor = donorManager.getDonorByUserId(userId);

        if (donor == null) {
            // If donor does not exist, create new
            Donor newDonor = new Donor();
            newDonor.setUserId(userId);
            newDonor.setPoints(0);
            donorManager.addDonor(newDonor);
            donor = newDonor;
        }

        int points = calculatePoints(medicineType, quantity);
        donor.setPoints(donor.getPoints() + points);
        donorManager.updateDonor(donor);
    }

    public  int calculatePoints(String medicineType, int quantity) {
        switch (medicineType.toLowerCase()) {
            case "tablet": return 5 * quantity;
            case "capsule": return 5 * quantity;
            case "syrup": return 20 * quantity;
            case "injection": return 10 * quantity;
            default: return 1 * quantity;
        }
    }
}
