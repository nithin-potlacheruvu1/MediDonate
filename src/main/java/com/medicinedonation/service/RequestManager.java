package com.medicinedonation.service;

import java.util.ArrayList;
import java.util.List;

import com.medicinedonation.dao.RequestDAO;
import com.medicinedonation.daoImpl.RequestDAOImpl;
import com.medicinedonation.model.Request;
import com.medicinedonation.model.User;

public class RequestManager {
    private RequestDAO requestDao = new RequestDAOImpl();
    private UserManager userManager = new UserManager();
    private NotificationService notificationService = new NotificationService();

    
    
    public void addRequest(Request request) throws Exception {
    	 Request.Validator.validate(request);
        requestDao.insertRequest(request);

        // Notify recipient
        User recipient = userManager.getUser(request.getUserId());
        notificationService.notifyUser(recipient, "Your medicine request has been created!");
    }

    public void updateRequest(Request request) throws Exception {
        requestDao.updateRequest(request);

        // Notify recipient
        User recipient = userManager.getUser(request.getUserId());
        notificationService.notifyUser(recipient, "Your request details have been updated.");
    }

    public void removeRequest(int requestId) throws Exception {
        requestDao.deleteRequest(requestId);
    }

    public Request getRequest(int requestId) throws Exception {
        return requestDao.getRequestById(requestId);
    }

    public List<Request> getAllRequests() throws Exception {
        return requestDao.getAllRequests();
    }
    
    public List<Request> getRequestsByUserId(int userId) throws Exception {
        List<Request> allRequests = requestDao.getAllRequests();
        List<Request> userRequests = new ArrayList<>();
        for (Request r : allRequests) {
            if (r.getUserId() == userId) {
                userRequests.add(r);
            }
        }
        return userRequests;
    }


  
}
