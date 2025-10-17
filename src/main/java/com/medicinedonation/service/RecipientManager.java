package com.medicinedonation.service;

import java.util.List;

import com.medicinedonation.dao.RecipientDAO;
import com.medicinedonation.daoImpl.RecipientDAOImpl;
import com.medicinedonation.model.Recipient;

public class RecipientManager {
    private RecipientDAO recipientDAO = new RecipientDAOImpl();

    public void addRecipient(Recipient recipient) throws Exception {
        recipientDAO.insertRecipient(recipient);
    }

    public void updateRecipient(Recipient recipient) {
        try {
            int rows = recipientDAO.updateRecipient(recipient);
            if (rows > 0) {
                System.out.println("Recipient updated!");
            } else {
                System.out.println("Recipient ID " + recipient.getUserId() + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error updating recipient: " + e.getMessage());
        }
    }

    public void removeRecipient(int recipientId) {
        try {
            int rows = recipientDAO.deleteRecipient(recipientId);
            if (rows > 0) {
                System.out.println("Recipient removed!");
            } else {
                System.out.println("Recipient ID " + recipientId + " does not exist!");
            }
        } catch (Exception e) {
            System.out.println("Error removing recipient: " + e.getMessage());
        }
    }


    public Recipient getRecipient(int recipientId) throws Exception {
        return recipientDAO.getRecipientById(recipientId);
    }

    public List<Recipient> getAllRecipients() throws Exception {
        return recipientDAO.getAllRecipients();
    }
    
    public Recipient getRecipientByUserId(int userId) throws Exception {
        return recipientDAO.getRecipientByUserId(userId);
    }
    
    

}
