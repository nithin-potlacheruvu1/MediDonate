package com.medicinedonation.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.medicinedonation.model.Donation;
import com.medicinedonation.model.Donor;
import com.medicinedonation.model.User;
import com.medicinedonation.service.DonationManager;
import com.medicinedonation.service.DonorManager;

@WebServlet("/DonorServlet")
public class DonorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // --- Validate input ---
            String medicineIdStr = request.getParameter("medicineId");
            String quantityStr = request.getParameter("quantity");

            if (medicineIdStr == null || medicineIdStr.isEmpty()) {
                throw new Exception("Please select a medicine.");
            }

            int medicineId = Integer.parseInt(medicineIdStr);
            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) throw new Exception("Quantity must be positive.");
            } catch (NumberFormatException e) {
                throw new Exception("Invalid quantity.");
            }

            // --- Get or create donor ---
            DonorManager donorManager = new DonorManager();
            Donor donor = donorManager.getDonorByUserId(user.getUserId());
            if (donor == null) {
                Donor newDonor = new Donor(user.getUserId(), 0);
                donorManager.addDonor(newDonor);
                donor = donorManager.getDonorByUserId(user.getUserId());
            }

            // --- Record donation ---
            Donation donation = new Donation();
            donation.setUserId(user.getUserId()); // link donation to user
            donation.setMedicineId(medicineId);
            donation.setQuantity(quantity);
            donation.setDonationDate(new java.sql.Date(System.currentTimeMillis()));

            DonationManager donationManager = new DonationManager();
            donationManager.addDonation(donation);

            // --- Update donor points ---
            donor.setPoints(donor.getPoints() + 10); // add 10 points per donation
            donorManager.updateDonor(donor);

            request.setAttribute("message", "Donation recorded successfully!");
            request.getRequestDispatcher("donation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to record donation. Please try again.");
            request.getRequestDispatcher("donation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("donor.jsp"); // redirect if accessed directly
    }
}
