package com.medicinedonation.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicinedonation.ejb.RewardServiceEJB;
import com.medicinedonation.model.Donation;
import com.medicinedonation.model.Donor;
import com.medicinedonation.model.User;
import com.medicinedonation.service.DonationManager;
import com.medicinedonation.service.DonorManager;

@WebServlet("/DonationServlet")
public class DonationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DonationManager donationManager = new DonationManager();
    private DonorManager donorManager = new DonorManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // --- Step A: Check if user is already a donor ---
            Donor donor = donorManager.getDonorByUserId(user.getUserId());
            if (donor == null) {
                // First time donating → create donor entry
                Donor newDonor = new Donor();
                newDonor.setUserId(user.getUserId());
                newDonor.setPoints(0);
                donorManager.addDonor(newDonor);

                donor = newDonor; // assign for donation
            }

         // --- Step B: Add donation ---
            int medicineId = Integer.parseInt(request.getParameter("medicineId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String medicineType = request.getParameter("mtype");

            // NEW: parse expiry date from request
            String expiryStr = request.getParameter("expiry"); 
            java.sql.Date expiryDate = java.sql.Date.valueOf(expiryStr);

            Donation donation = new Donation();
            donation.setUserId(user.getUserId()); // link donation to user
            donation.setMedicineId(medicineId);
            donation.setQuantity(quantity);
            donation.setDonationDate(new java.sql.Date(System.currentTimeMillis()));
            donation.setMedicineType(medicineType);  
            donation.setExpiryDate(expiryDate); // <-- set expiry date
            donationManager.addDonation(donation);

            try {
                RewardServiceEJB rewardService = new RewardServiceEJB();
                rewardService.addRewardPoints(user.getUserId(), medicineType, quantity);
            } catch (Exception e) {
                e.printStackTrace();
            }

         // After adding donation and updating points
            int totalPoints = donorManager.getDonorByUserId(user.getUserId()).getPoints();
            response.sendRedirect("donation.jsp?success=1&points=" + totalPoints);

            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("donation.jsp?error=1");
        }
    }
}
