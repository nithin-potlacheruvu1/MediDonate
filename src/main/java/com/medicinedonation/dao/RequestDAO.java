package com.medicinedonation.dao;

import com.medicinedonation.model.Request;
import java.util.List;

public interface RequestDAO {
    void insertRequest(Request request);
    void updateRequest(Request request);
    void deleteRequest(int requestId);
    Request getRequestById(int requestId);
    List<Request> getAllRequests();
	Request getRequestByUserId(int userId);
}

