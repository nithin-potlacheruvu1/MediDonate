package com.medicinedonation.dao;

import com.medicinedonation.model.Recipient;
import java.util.List;

public interface RecipientDAO {
    int insertRecipient(Recipient recipient) throws Exception;
    int updateRecipient(Recipient recipient) throws Exception;
    int deleteRecipient(int recipientId) throws Exception;
    Recipient getRecipientById(int recipientId) throws Exception;
    Recipient getRecipientByUserId(int userId) throws Exception;
    public List<Recipient> getAllRecipients() throws Exception;

}
