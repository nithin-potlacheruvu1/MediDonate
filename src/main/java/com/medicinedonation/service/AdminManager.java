package com.medicinedonation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.medicinedonation.dao.DonationDAO;
import com.medicinedonation.dao.DonorDAO;
import com.medicinedonation.dao.RecipientDAO;
import com.medicinedonation.dao.RequestDAO;
import com.medicinedonation.daoImpl.DonationDAOImpl;
import com.medicinedonation.daoImpl.DonorDAOImpl;
import com.medicinedonation.daoImpl.RecipientDAOImpl;
import com.medicinedonation.daoImpl.RequestDAOImpl;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.Donation;
import com.medicinedonation.model.Donor;
import com.medicinedonation.model.Recipient;
import com.medicinedonation.model.Request;

public class AdminManager {

    private DonorDAO donorDAO;
    private RecipientDAO recipientDAO;
    private DonationDAO donationDAO;
    private RequestDAO requestDAO;

    public AdminManager() {
        donorDAO = new DonorDAOImpl();
        recipientDAO = new RecipientDAOImpl();
        donationDAO = new DonationDAOImpl();
        requestDAO = new RequestDAOImpl();
    }

    // Fetch all donors
    public List<Donor> getAllDonors() {
        return donorDAO.getAllDonors();
    }

    // Fetch all recipients
    public List<Recipient> getAllRecipients() {
        try {
            return recipientDAO.getAllRecipients();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fetch all donations
    public List<Donation> getAllDonations() {
        try {
            return donationDAO.getAllDonations();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fetch all requests
    public List<Request> getAllRequests() {
        try {
            return requestDAO.getAllRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Approve request
    public void approveRequest(int requestId) {
        try {
            Request req = requestDAO.getRequestById(requestId);
            if (req != null) {
                req.setStatus("Approved");
                requestDAO.updateRequest(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reject request
    public void rejectRequest(int requestId) {
        try {
            Request req = requestDAO.getRequestById(requestId);
            if (req != null) {
                req.setStatus("Rejected");
                requestDAO.updateRequest(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean updateRequestStatus(int requestId, String status) {
        String sql = "UPDATE requests SET status=? WHERE request_id=?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, requestId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
